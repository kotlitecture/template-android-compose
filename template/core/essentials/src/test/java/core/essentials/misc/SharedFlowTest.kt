package core.essentials.misc

import core.essentials.AbstractTest
import core.essentials.cache.ICacheKey
import core.essentials.cache.ICacheSource
import core.essentials.cache.impl.MemoryCacheSource
import core.essentials.misc.extensions.launchGlobalAsync
import core.essentials.misc.extensions.sharedFlow
import io.ktor.util.collections.ConcurrentSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.isActive
import org.junit.jupiter.api.Assertions
import org.tinylog.Logger
import java.util.UUID
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class SharedFlowTest : AbstractTest() {

    private val cache: ICacheSource = MemoryCacheSource()

    @Test
    fun `shared flow is correctly shared`() = performTest {
        val iterations = 100
        val cached = ConcurrentSet<String>()
        val completed = ConcurrentSet<String>()
        val flow = sharedFlow<Unit> {
            val uid = UUID.randomUUID().toString()
            cached.add(uid)
            callbackFlow<Int> {
                while (isActive) {
                    Logger.debug("iterate :: {}", uid)
                    delay(3.seconds)
                }
            }.onCompletion { completed.add(uid) }.collect()
        }

        // subscribe
        repeat(iterations) { iteration ->
            launchGlobalAsync(iteration.toString()) {
                cache.get(CacheKey(iteration)) {
                    Logger.debug("collect from :: first {}", iteration)
                    delay(1.seconds)
                    flow.collect()
                    iteration
                }
            }
        }
        delay(3.seconds)
        Assertions.assertEquals(1, cached.size)
        Assertions.assertEquals(0, completed.size)

        // cancel all jobs
        repeat(iterations) { iteration ->
            launchGlobalAsync(iteration.toString()) {}
        }
        delay(1.seconds)
        Assertions.assertEquals(1, cached.size)
        Assertions.assertEquals(1, completed.size)

        // subscribe again
        repeat(iterations) { iteration ->
            launchGlobalAsync(iteration.toString()) {
                cache.get(CacheKey(iteration)) {
                    Logger.debug("collect from :: second {}", iteration)
                    delay(1.seconds)
                    flow.collect()
                    iteration
                }
            }
        }
        delay(3.seconds)
        // cancel all jobs
        repeat(iterations) { iteration ->
            launchGlobalAsync(iteration.toString()) {}
        }
        delay(1.seconds)
        Assertions.assertEquals(2, completed.size)
        Assertions.assertEquals(2, cached.size)
    }

    private data class CacheKey(
        val id: Int,
        override val ttl: Long = ICacheKey.TTL_1_SECOND
    ) : ICacheKey<Int>

}