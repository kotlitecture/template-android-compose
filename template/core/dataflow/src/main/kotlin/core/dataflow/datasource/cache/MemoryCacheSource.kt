@file:OptIn(DelicateCoroutinesApi::class)
@file:Suppress("UNCHECKED_CAST")

package core.dataflow.datasource.cache

import core.dataflow.misc.extensions.isCancellationException
import core.dataflow.misc.extensions.isIgnoredException
import core.dataflow.misc.extensions.isTimeoutException
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
import org.tinylog.kotlin.Logger
import java.util.Date
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.seconds

class MemoryCacheSource : CacheSource {

    private val dispatcher = Dispatchers.IO
    private val jobs = ConcurrentHashMap<CacheKey, Deferred<*>>()
    private val cache = ConcurrentHashMap<CacheKey, CacheData>()

    override suspend fun <T> getState(
        key: core.dataflow.datasource.cache.CacheKey<T>,
        valueProvider: suspend () -> T?
    ): CacheState<T> {
        val cacheKey = CacheKey(key)
        val cacheItem = cache[cacheKey]
        return object : CacheState<T> {
            override val key: core.dataflow.datasource.cache.CacheKey<T> = key
            override var value: T? = cacheItem?.data as? T
            override suspend fun getLast(): T? = value ?: get()
            override suspend fun get(): T? = get(key, valueProvider)
            override suspend fun getFresh(): T? {
                cache[cacheKey]?.invalidate()
                return get()
            }

            override suspend fun getChanges(): Flow<T> = flow<T> {
                value?.let { emit(it) }
                while (currentCoroutineContext().isActive) {
                    try {
                        val fresh = get()
                        if (fresh != null) {
                            emit(fresh)
                            delay(cacheKey.key.ttl)
                        }
                    } catch (e: Exception) {
                        when {
                            !e.isIgnoredException() -> delay(3000L)
                            !e.isTimeoutException() -> throw e
                            else -> Unit
                        }
                    }
                }
            }.distinctUntilChanged().retry { true.also { delay(1.seconds) } }
        }
    }

    override suspend fun <T> get(key: core.dataflow.datasource.cache.CacheKey<T>, valueProvider: suspend () -> T?): T? {
        val cacheKey = CacheKey(key)
        val cacheItem = cache[cacheKey]
        if (cacheItem == null || !cacheItem.isValid(key.ttl)) {
            val data = getValue(cacheKey, valueProvider) ?: return null
            cache[cacheKey] = CacheData(data)
            return data
        } else {
            Logger.debug("get from cache :: key={}, size={}", key, cache.size)
            return cacheItem.data as T?
        }
    }

    override suspend fun <T> put(key: core.dataflow.datasource.cache.CacheKey<T>, value: T) {
        val cacheKey = CacheKey(key)
        cache[cacheKey] = CacheData(value)
    }

    override fun clear() {
        jobs.onEach { it.value.cancel() }
        jobs.clear()
        cache.clear()
    }

    override fun <K : core.dataflow.datasource.cache.CacheKey<*>> invalidate(type: Class<K>) {
        jobs.iterator().forEachRemaining { entry ->
            val job = entry.value
            val key = entry.key
            if (key.type == type) {
                Logger.debug("remove key :: {}", job.key)
                jobs.remove(key, job)
                job.cancel()
            }
        }
        cache.iterator().forEachRemaining { entry ->
            if (entry.key.type == type) {
                Logger.debug("remove cache entry :: {}", entry.key)
                entry.value.invalidate()
            }
        }
    }

    override fun <K : core.dataflow.datasource.cache.CacheKey<*>> invalidate(key: K) {
        val cacheKey = CacheKey(key)
        jobs.remove(cacheKey)?.cancel()
        cache[cacheKey]?.invalidate()
    }

    override fun <K : core.dataflow.datasource.cache.CacheKey<*>> remove(type: Class<K>) {
        jobs.iterator().forEachRemaining { entry ->
            val job = entry.value
            val key = entry.key
            if (key.type == type) {
                Logger.debug("remove key :: {}", job.key)
                jobs.remove(key, job)
                job.cancel()
            }
        }
        cache.iterator().forEachRemaining { entry ->
            if (entry.key.type == type) {
                Logger.debug("remove cache entry :: {}", entry.key)
                cache.remove(entry.key, entry.value)
            }
        }
    }

    override fun <K : core.dataflow.datasource.cache.CacheKey<*>> remove(key: K) {
        val cacheKey = CacheKey(key)
        jobs.remove(cacheKey)?.cancel()
        cache.remove(cacheKey)
    }

    private suspend fun <T> getValue(cacheKey: CacheKey, valueProvider: suspend () -> T?): T? {
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
                if (cacheKey.key.isImmortal()) {
                    jobs.computeIfAbsent(cacheKey) {
                        GlobalScope.async {
                            Logger.info("put into cache :: {} - {}", cacheKey.hashCode(), cacheKey)
                            runCatching { valueProvider() }
                                .onFailure { Logger.error(it, "getValue :: {}", cacheKey) }
                                .onSuccess { Logger.info("putValue :: {} -> {}", cacheKey, it) }
                                .getOrThrow()
                        }
                    }
                } else {
                    withContext(dispatcher) {
                        jobs.computeIfAbsent(cacheKey) {
                            async {
                                Logger.info("put into cache :: {} - {}", cacheKey.hashCode(), cacheKey)
                                runCatching { valueProvider() }
                                    .onFailure {
                                        if (!it.isCancellationException()) {
                                            Logger.error(it, "getValue :: {}", cacheKey)
                                        }
                                    }
                                    .getOrThrow()
                            }
                        }
                    }
                }
            }
        job.invokeOnCompletion { jobs.remove(cacheKey, job) }
        return runCatching { job.await() as? T }
            .let { if (cacheKey.key.throwErrors()) it.getOrThrow() else it.getOrNull() }
    }

    private data class CacheData(
        val data: Any?,
        val createDate: Date = Date()
    ) {
        private var invalid: Boolean = false

        fun isValid(ttl: Long): Boolean = when {
            invalid -> false
            data == null -> false
            ttl > 0 -> createDate.time + ttl > System.currentTimeMillis()
            ttl == 0L -> false
            else -> true
        }

        fun invalidate() {
            invalid = true
        }
    }

    private data class CacheKey(
        val key: core.dataflow.datasource.cache.CacheKey<*>,
        val type: Class<*> = key.javaClass
    )

}