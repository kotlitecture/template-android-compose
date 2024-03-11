package core.dataflow.misc

import core.dataflow.datasource.cache.CacheKey
import core.dataflow.datasource.cache.CacheSource
import core.dataflow.datasource.cache.MemoryCacheSource
import core.dataflow.misc.extensions.launchGlobalAsync
import core.dataflow.misc.extensions.sharedFlow
import core.testing.MyUnitTest
import io.ktor.util.collections.ConcurrentSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.isActive
import org.junit.Assert
import org.tinylog.Logger
import java.util.UUID
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class SharedFlowTest : MyUnitTest() {

    private val cache: CacheSource = MemoryCacheSource()

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
                cache.get(TestCacheKey(iteration)) {
                    Logger.debug("collect from :: first {}", iteration)
                    delay(1.seconds)
                    flow.collect()
                    iteration
                }
            }
        }
        delay(3.seconds)
        Assert.assertEquals(1, cached.size)
        Assert.assertEquals(0, completed.size)

        // cancel all jobs
        repeat(iterations) { iteration ->
            launchGlobalAsync(iteration.toString()) {}
        }
        delay(1.seconds)
        Assert.assertEquals(1, cached.size)
        Assert.assertEquals(1, completed.size)

        // subscribe again
        repeat(iterations) { iteration ->
            launchGlobalAsync(iteration.toString()) {
                cache.get(TestCacheKey(iteration)) {
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
        Assert.assertEquals(2, completed.size)
        Assert.assertEquals(2, cached.size)
    }

    private data class TestCacheKey(
        val id: Int,
        override val ttl: Long = CacheKey.TTL_1_SECOND
    ) : CacheKey<Int>

}