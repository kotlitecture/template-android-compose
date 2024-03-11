package core.ui.app.usecase

import core.testing.BaseAndroidHiltUnitTest
import dagger.hilt.android.testing.HiltAndroidTest
import io.ktor.util.collections.ConcurrentSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@HiltAndroidTest
class UseCaseTest : BaseAndroidHiltUnitTest() {

    @Inject
    lateinit var sampleUseCase: SampleUseCase

    @Inject
    lateinit var sampleGlobalUseCase: SampleGlobalUseCase

    @Test
    fun `when cancel all running task`() = performTest {
        val context = sampleUseCase.proceed(Unit)
        context.cancel()
        val data = context.awaitNotNull()
        Assert.assertEquals(0, data.size)
        Assert.assertEquals(3, context.jobs.size)
    }

    @Test
    fun `when await all running task`() = performTest {
        val context = sampleUseCase.proceed(Unit)
        val data = context.awaitNotNull()
        Assert.assertEquals(3, data.size)
        Assert.assertEquals(3, context.jobs.size)
    }

    @Test
    fun `when await last running task`() = performTest {
        val context = sampleUseCase.proceed(Unit)
        context.jobs.last().await()
        context.cancel()
        val data = context.awaitNotNull()
        Assert.assertEquals(1, data.size)
        Assert.assertEquals(SampleUseCase.JOB_3, data[SampleUseCase.JOB_3])
        Assert.assertEquals(3, context.jobs.size)
    }

    @Test
    fun `when await first running task`() = performTest {
        val context = sampleUseCase.proceed(Unit)
        context.jobs.first().await()
        context.cancel()
        val data = context.awaitNotNull()
        Assert.assertEquals(2, data.size)
        Assert.assertEquals(SampleUseCase.JOB_1, data[SampleUseCase.JOB_1])
        Assert.assertEquals(SampleUseCase.JOB_3, data[SampleUseCase.JOB_3])
        Assert.assertEquals(3, context.jobs.size)
    }

    @Test
    fun `when launch global use case`() = performTest {
        val context1 = sampleGlobalUseCase.proceed("111")
        val context2 = sampleGlobalUseCase.proceed("111")
        Assert.assertSame(context1, context2)

        val context3 = sampleGlobalUseCase.proceed("222")
        Assert.assertEquals("222", context3.input)
        Assert.assertNotSame(context1, context3)
        Assert.assertFalse(context1.isActive())
        Assert.assertTrue(context3.isActive())
        context3.await()
        Assert.assertFalse(context3.isActive())

        val context4 = sampleGlobalUseCase.proceed("222")
        Assert.assertEquals(context3.input, context4.input)
        Assert.assertNotSame(context3, context4)
    }

    @Test
    fun `when launch same global from multiple threads`() = performTest {
        val iterations = 100
        val cached = ConcurrentSet<UseCase.Context<out Any, out Any>>()
        repeat(iterations) {
            launch {
                val context = sampleGlobalUseCase.proceed("111")
                cached.add(context)
            }
        }
        delay(3.seconds)
        Assert.assertEquals(1, cached.size)
    }

    @Test
    fun `when launch different global from multiple threads`() = performTest {
        val iterations = 100
        val cached = ConcurrentLinkedQueue<UseCase.Context<out String, out Map<String, String>>>()
        repeat(iterations) {
            launch {
                val context = sampleGlobalUseCase.proceed(it.toString())
                cached.add(context)
            }
        }
        delay(2.seconds)
        Assert.assertEquals(iterations, cached.distinct().size)
        cached.take(iterations - 1).forEach {
            Assert.assertTrue(!it.isActive())
            Assert.assertNull(it.output)
        }
        Assert.assertEquals(3, cached.last().awaitNotNull().size)
    }

}

@Singleton
class SampleUseCase @Inject constructor() : UseCase<Unit, Map<String, String>>() {

    override suspend fun doProceed(context: Context<Unit, Map<String, String>>) {
        val map = mutableMapOf<String, String>()
        withDeferred(context) {
            delay(2.seconds)
            map[JOB_1] = JOB_1
        }
        withDeferred(context) {
            delay(4.seconds)
            map[JOB_2] = JOB_2
        }
        withDeferred(context) {
            delay(1.seconds)
            map[JOB_3] = JOB_3
        }
        context.output = map
    }

    companion object {
        const val JOB_1 = "job_1"
        const val JOB_2 = "job_2"
        const val JOB_3 = "job_3"
    }
}

@Singleton
class SampleGlobalUseCase @Inject constructor() : GlobalUseCase<String, Map<String, String>>() {

    override fun getId(): String = "global"

    override suspend fun doProceed(context: Context<String, Map<String, String>>) {
        val map = mutableMapOf<String, String>()
        withDeferred(context) {
            delay(2.seconds)
            map[JOB_1] = JOB_1
        }
        withDeferred(context) {
            delay(4.seconds)
            map[JOB_2] = JOB_2
        }
        withDeferred(context) {
            delay(1.seconds)
            map[JOB_3] = JOB_3
        }
        delay(3.seconds)
        context.output = map
    }

    companion object {
        const val JOB_1 = "job_1"
        const val JOB_2 = "job_2"
        const val JOB_3 = "job_3"
    }
}