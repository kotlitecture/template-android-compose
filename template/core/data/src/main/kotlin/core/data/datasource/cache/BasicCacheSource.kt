@file:OptIn(DelicateCoroutinesApi::class)
@file:Suppress("UNCHECKED_CAST")

package core.data.datasource.cache

import core.data.misc.extensions.isCancellationException
import core.data.misc.extensions.isIgnoredException
import core.data.misc.extensions.isTimeoutException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.concurrent.ConcurrentHashMap

/**
 * Basic implementation of a thread-safe cache for storing and retrieving in-memory data.
 * This cache can be utilized as an L1 Cache when managing HTTP requests, offering an efficient means
 * to present data without delays, but with the ability to update based on expiration and other conditions.
 *
 * @param changesRetryInterval The interval, in milliseconds, to retry cache changes.
 * @param exceptionRetryInterval The interval, in milliseconds, to retry cache operations in case of exceptions.
 * @param exceptionRetryCount The maximum number of retries for cache operations in case of exceptions.
 */
open class BasicCacheSource(
    private val changesRetryInterval: Long = 1000L,
    private val exceptionRetryInterval: Long = 3000L,
    private val exceptionRetryCount: Int = Int.MAX_VALUE
) : CacheSource {

    private val dispatcher = Dispatchers.IO
    private val jobs = ConcurrentHashMap<CacheKeySnapshot, Deferred<*>>()
    private val cache = ConcurrentHashMap<CacheKeySnapshot, CacheData>()

    override fun <T> getState(
        key: CacheKey<T>,
        valueProvider: suspend () -> T?
    ): CacheState<T> {
        val cacheKey = CacheKeySnapshot(key)
        return object : CacheState<T> {
            override val key: CacheKey<T> = key
            override suspend fun last(): T? = cache[cacheKey]?.data as? T
            override suspend fun get(): T? = get(key, valueProvider)
            override suspend fun fresh(): T? {
                cache[cacheKey]?.invalidate()
                return get()
            }

            override suspend fun changes(): Flow<T> = flow<T> {
                cache[cacheKey]?.data?.let { it as? T }?.let { emit(it) }
                var attempt = 0
                while (currentCoroutineContext().isActive) {
                    try {
                        val fresh = get()
                        if (fresh != null) {
                            emit(fresh)
                        }
                        delay(cacheKey.key.ttl)
                        attempt = 0
                    } catch (e: Exception) {
                        attempt++
                        when {
                            attempt >= exceptionRetryCount -> throw e
                            !e.isIgnoredException() -> delay(exceptionRetryInterval)
                            !e.isTimeoutException() -> throw e
                            else -> Unit
                        }
                    }
                }
            }.distinctUntilChanged().retry { !it.isCancellationException().also { delay(changesRetryInterval) } }
        }
    }

    override suspend fun <T> get(key: CacheKey<T>, valueProvider: suspend () -> T?): T? {
        val cacheKey = CacheKeySnapshot(key)
        val cacheItem = cache[cacheKey]
        if (cacheItem == null || !cacheItem.isValid(key.ttl)) {
            val data = getValue(cacheKey, valueProvider) ?: return null
            cache[cacheKey] = CacheData(data)
            return data
        } else {
            return cacheItem.data as T?
        }
    }

    override fun <T> put(key: CacheKey<T>, value: T) {
        val cacheKey = CacheKeySnapshot(key)
        cache[cacheKey] = CacheData(value)
    }

    override fun clear() {
        jobs.onEach { it.value.cancel() }
        jobs.clear()
        cache.clear()
    }

    override fun <K : CacheKey<*>> invalidate(type: Class<K>) {
        jobs.iterator().forEachRemaining { entry ->
            val job = entry.value
            val key = entry.key
            if (key.type == type) {
                jobs.remove(key, job)
                job.cancel()
            }
        }
        cache.iterator().forEachRemaining { entry ->
            if (entry.key.type == type) {
                entry.value.invalidate()
            }
        }
    }

    override fun <K : CacheKey<*>> invalidate(key: K) {
        val cacheKey = CacheKeySnapshot(key)
        jobs.remove(cacheKey)?.cancel()
        cache[cacheKey]?.invalidate()
    }

    override fun <K : CacheKey<*>> remove(type: Class<K>) {
        jobs.iterator().forEachRemaining { entry ->
            val job = entry.value
            val key = entry.key
            if (key.type == type) {
                jobs.remove(key, job)
                job.cancel()
            }
        }
        cache.iterator().forEachRemaining { entry ->
            if (entry.key.type == type) {
                cache.remove(entry.key, entry.value)
            }
        }
    }

    override fun <K : CacheKey<*>> remove(key: K) {
        val cacheKey = CacheKeySnapshot(key)
        jobs.remove(cacheKey)?.cancel()
        cache.remove(cacheKey)
    }

    private suspend fun <T> getValue(cacheKey: CacheKeySnapshot, valueProvider: suspend () -> T?): T? {
        val job = jobs[cacheKey]
            ?.let {
                if (it.isCancelled) {
                    jobs.remove(cacheKey, it)
                    null
                } else {
                    it
                }
            }
            ?: run {
                if (cacheKey.key.immortal()) {
                    jobs.computeIfAbsent(cacheKey) {
                        GlobalScope.async { valueProvider() }
                    }
                } else {
                    withContext(dispatcher) {
                        jobs.computeIfAbsent(cacheKey) {
                            async { valueProvider() }
                        }
                    }
                }
            }
        job.invokeOnCompletion { jobs.remove(cacheKey, job) }
        return job.await() as? T
    }

    private data class CacheData(
        val data: Any?,
        val createDate: Date = Date()
    ) {
        private var invalid: Boolean = false

        fun isValid(ttl: Long): Boolean = when {
            invalid -> false
            data == null -> false
            ttl > 0 -> !isExpired(ttl)
            ttl == 0L -> false
            else -> true
        }

        fun isExpired(ttl: Long): Boolean {
            return ttl > 0 && createDate.time + ttl <= System.currentTimeMillis()
        }

        fun invalidate() {
            invalid = true
        }
    }

    private data class CacheKeySnapshot(
        val key: CacheKey<*>,
        val type: Class<*> = key.javaClass
    )

}