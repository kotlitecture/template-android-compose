package core.data.misc.helpers

import core.data.exception.DataException
import kotlinx.coroutines.delay
import org.tinylog.Logger

class RateLimiter(
    limitPerSecond: Int,
    private val retryDelay: Long = 5_000L,
    private val retries: Int = 2,
) {

    private val limiter = RequestThrottler(limitPerSecond)
    private val minDuration = 1000 / limitPerSecond

    suspend fun <T> limit(id: String, action: suspend () -> T): T {
        val result: T = limiter.throttle(id) {
            for (i in 0..retries) {
                try {
                    val time = System.currentTimeMillis()
                    val result = action.invoke()
                    val remainingDelay = System.currentTimeMillis() - time
                    if (remainingDelay < minDuration) {
                        Logger.debug("helper :: limit :: {}", remainingDelay)
                        delay(remainingDelay)
                    }
                    return@throttle result
                } catch (e: DataException) {
                    if (i == retries || e.code != 429) {
                        throw e
                    } else {
                        Logger.error(e, "rate limited or other shit. try to cool down")
                        delay(retryDelay)
                    }
                }
            }
            throw DataException(0, "idk")
        }
        return result
    }

}