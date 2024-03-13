package core.data.datasource.cache

import core.data.misc.extensions.globalAsync
import core.testing.BaseUnitTest
import io.ktor.util.collections.ConcurrentSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Assert
import org.tinylog.kotlin.Logger
import java.util.UUID
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class CacheDataSourceTest : BaseUnitTest() {

    private val cache: CacheSource = MemoryCacheSource()

    @Test
    fun `make sure all cache actions are performed in not blocking way`() = performTest {
        val iterations = 1000
        val cached = ConcurrentSet<Int>()
        repeat(iterations) { iteration ->
            globalAsync(iteration.toString()) {
                cache
                    .get(TestCacheKey(iteration)) {
                        delay(2.seconds)
                        iteration
                    }
                    ?.let(cached::add)
            }
        }
        delay(3.seconds)
        Assert.assertEquals(iterations, cached.size)
        Logger.debug("cached :: {}", cached.toList())
    }

    @Test
    fun `make sure same actions use the same cached value`() = performTest {
        val key = UUIDCacheKey(Int.MAX_VALUE)
        val iterations = 1000
        val cached = ConcurrentSet<UUID>()
        repeat(iterations) {
            launch {
                delay(300)
                cache
                    .get(key) {
                        delay(1.seconds)
                        UUID.randomUUID()
                    }
                    ?.let(cached::add)
            }
        }
        delay(2.seconds)
        Assert.assertEquals(1, cached.size)
    }

    @Test
    fun `check cached state logic`() = performTest {
        val key = UUIDCacheKey(Int.MAX_VALUE, ttl = 100)
        val valueState = cache.getState(key) { UUID.randomUUID() }
        val value1 = valueState.get()
        val value1Last = valueState.getLast()
        delay(100)
        val value2 = valueState.get()
        delay(100)
        Assert.assertNotEquals(value1, value2)
        Assert.assertEquals(value1, value1Last)
        Assert.assertNotEquals(value2, valueState.get())
    }

    private data class TestCacheKey(
        val id: Int,
        override val ttl: Long = CacheKey.TTL_UNLIMITED
    ) : CacheKey<Int>

    private data class UUIDCacheKey(
        val id: Int,
        override val ttl: Long = CacheKey.TTL_UNLIMITED
    ) : CacheKey<UUID>

}