package core.essentials.cache

import core.essentials.AbstractTest
import core.essentials.cache.ICacheKey
import core.essentials.cache.ICacheSource
import core.essentials.cache.impl.MemoryCacheSource
import core.essentials.misc.extensions.launchGlobalAsync
import io.ktor.util.collections.ConcurrentSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions
import org.tinylog.kotlin.Logger
import java.util.UUID
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class CacheDataSourceTest : AbstractTest() {

    private val cache: ICacheSource = MemoryCacheSource()

    @Test
    fun `make sure all cache actions are performed in not blocking way`() = performTest {
        val iterations = 1000
        val cached = ConcurrentSet<Int>()
        repeat(iterations) { iteration ->
            launchGlobalAsync(iteration.toString()) {
                cache
                    .get(CacheKey(iteration)) {
                        delay(2.seconds)
                        iteration
                    }
                    ?.let(cached::add)
            }
        }
        delay(3.seconds)
        Assertions.assertEquals(iterations, cached.size)
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
        Assertions.assertEquals(1, cached.size)
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
        Assertions.assertNotEquals(value1, value2)
        Assertions.assertEquals(value1, value1Last)
        Assertions.assertNotEquals(value2, valueState.get())
    }

    private data class CacheKey(
        val id: Int,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<Int>

    private data class UUIDCacheKey(
        val id: Int,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<UUID>

}