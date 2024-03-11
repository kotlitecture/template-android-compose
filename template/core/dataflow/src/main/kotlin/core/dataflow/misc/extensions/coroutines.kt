@file:OptIn(DelicateCoroutinesApi::class)

package core.dataflow.misc.extensions

import core.dataflow.exception.DataException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.isActive
import org.tinylog.Logger
import java.util.concurrent.CancellationException
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.coroutineContext

private val globalScope = GlobalScope
private val jobs = ConcurrentHashMap<String, Deferred<Any>>()

fun <T> infiniteFlow(
    id: String,
    retryTimeout: Long = 1000L,
    onCreate: suspend FlowCollector<T>.() -> Unit = {},
    onNext: suspend FlowCollector<T>.(iteration: Int) -> Long
): Flow<T> =
    flow {
        var iteration = 0
        runCatching { onCreate() }
        while (coroutineContext.isActive) {
            try {
                Logger.debug("iterate next :: {} -> {}", id, iteration)
                val delay = onNext(iteration++)
                delay(delay)
            } catch (e: CancellationException) {
                Logger.error("infiniteFlow :: canceled :: {} - {}", id, e)
                return@flow
            } catch (e: Throwable) {
                Logger.error("infiniteFlow :: error :: {} - {}", id, e)
                delay(retryTimeout)
            }
        }
    }

fun <T> sharedFlow(stopTimeoutMillis: Long = 0, onInit: suspend FlowCollector<T>.() -> Unit = {}): Flow<T> {
    return flow { onInit() }.shareIn(globalScope, SharingStarted.WhileSubscribed(stopTimeoutMillis))
}

suspend fun <T> repeatWhile(
    interval: Long = 1500,
    repeatTimes: Int = 50,
    block: suspend () -> T,
    repeatIf: (value: T) -> Boolean
): T {
    var repeats = repeatTimes
    while (coroutineContext.isActive && --repeats > 0) {
        val value: T = block.invoke()
        if (!repeatIf(value)) {
            return value
        }
        delay(interval)
    }
    throw DataException(msg = "is not active anymore")
}

suspend fun <T> withJobAsync(block: suspend CoroutineScope.() -> T): Deferred<Result<T>> =
    globalScope.async(coroutineContext) { runCatching { block.invoke(this) } }

suspend fun launchAsync(
    id: String,
    forceNew: Boolean = true,
    block: suspend CoroutineScope.() -> Unit
): Deferred<Any> {
    val existing = jobs[id]
    if (!forceNew && existing != null && existing.isActive) {
        Logger.debug("launchAsync :: skip job due to currently running :: {}", id)
        return existing
    }
    existing?.cancel()
    val context = coroutineContext
    val job = globalScope.async(context) { runCatching { block.invoke(this) } }
    job.invokeOnCompletion { jobs.remove(id, job) }
    jobs[id] = job
    return job
}

fun launchGlobalAsync(
    id: String,
    forceNew: Boolean = true,
    onReady: (job: Deferred<Any>) -> Unit = {},
    block: suspend CoroutineScope.() -> Unit
) {
    val existing = jobs[id]
    if (!forceNew && existing != null && existing.isActive) {
        Logger.debug("launchGlobalAsync :: skip job due to currently running :: {}", id)
        onReady(existing)
    } else {
        Logger.debug("launchGlobalAsync :: start :: {}", id)
        existing?.cancel()
        val job = globalScope.async { runCatching { block.invoke(this) } }
        job.invokeOnCompletion { jobs.remove(id, job) }
        jobs[id] = job
        onReady(job)
    }
}