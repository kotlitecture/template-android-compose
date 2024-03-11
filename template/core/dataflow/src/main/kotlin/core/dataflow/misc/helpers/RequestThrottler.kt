package core.dataflow.misc.helpers

import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.util.concurrent.ConcurrentHashMap

class RequestThrottler(private val maxRequests: Int = 1) {

    private val semaphores = ConcurrentHashMap<String, Semaphore>()

    suspend fun <T> throttle(id: String, block: suspend () -> T): T {
        val semaphore = semaphores.computeIfAbsent(id) { Semaphore(maxRequests) }
        return semaphore.withPermit { block() }
    }

}