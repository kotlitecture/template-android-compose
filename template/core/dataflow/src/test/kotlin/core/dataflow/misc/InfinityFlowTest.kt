package core.dataflow.misc

import core.dataflow.datasource.cache.CacheKey
import core.dataflow.datasource.cache.CacheSource
import core.dataflow.datasource.cache.MemoryCacheSource
import core.dataflow.misc.extensions.infiniteFlow
import core.testing.MyUnitTest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.flow.toList
import org.junit.Assert
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

class InfinityFlowTest : MyUnitTest() {

    private val cache: CacheSource = MemoryCacheSource()

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
            val result = cache.get(TestCacheKey(iteration)) {
                errors[iteration]?.let { throw it }
                iteration
            }
            if (result != null) {
                emit(result)
            }
            interval
        }.timeout(2.seconds).toList()
        Assert.assertEquals(3, list.size)
    }

    private data class TestCacheKey(
        val id: Int,
        override val ttl: Long = CacheKey.TTL_UNLIMITED
    ) : CacheKey<Int>

}