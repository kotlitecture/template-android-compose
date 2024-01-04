package core.essentials.misc

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.flow.toList
import core.essentials.AbstractTest
import core.essentials.cache.ICacheKey
import core.essentials.cache.ICacheSource
import core.essentials.cache.impl.MemoryCacheSource
import core.essentials.misc.extensions.infiniteFlow
import org.junit.jupiter.api.Assertions
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class InfinityFlowTest : AbstractTest() {

    private val cache: ICacheSource = MemoryCacheSource()

    @Test
    fun `make sure flow is stable with child context`() = performTest {
        val errors = listOf(
            null,
            RuntimeException(),
            null,
            FileNotFoundException(),
            null,
            IOException()
        )
        val interval = 10L
        val list = infiniteFlow("test", interval) { iteration ->
            if (iteration >= errors.size) throw CancellationException()
            val result = cache.get(CacheKey(iteration)) {
                errors[iteration]?.let { throw it }
                iteration
            }
            if (result != null) {
                emit(result)
            }
            interval
        }.timeout(2.seconds).toList()
        Assertions.assertEquals(3, list.size)
    }

    private data class CacheKey(
        val id: Int,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<Int>

}