package core.datasource.work

import core.datasource.work.model.WorkFilter
import core.datasource.work.model.WorkState
import core.datasource.work.worker.HelloUserGroupWorker
import core.datasource.work.worker.HelloUserWorker
import core.essentials.misc.extensions.repeatWhile
import core.testing.MyUnitTest
import dagger.hilt.android.testing.HiltAndroidTest
import io.ktor.util.collections.ConcurrentSet
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.junit.Assert
import org.junit.Test
import org.tinylog.Logger
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltAndroidTest
class WorkSourceTest : MyUnitTest() {

    @Inject
    lateinit var source: IWorkSource

    @Test
    fun `when work exists and accessed from one thread`() = performTest {
        val iterations = 1000
        val flow = source.create(HelloUserWorker::class.java, "Bob")
        val results = ConcurrentSet<String>()

        repeat(iterations) { results.add(flow.execute()!!) }

        Assert.assertEquals(1, results.size)
        Assert.assertEquals(WorkState.Completed, flow.getState())
    }

    @Test
    fun `when work exists and accessed from multiple threads`() = performTest {
        val iterations = 1000
        val flow = source.create(HelloUserWorker::class.java, "Bob")
        val results = ConcurrentSet<String>()

        val tasks = (1..iterations).map { async { results.add(flow.execute()!!) } }
        tasks.awaitAll()

        Assert.assertEquals(1, results.size)
        Assert.assertEquals(WorkState.Completed, flow.getState())
    }

    @Test
    fun `when enqueue work and get it in active`() = performTest {
        source.create(HelloUserWorker::class.java, "user")
        source.create(HelloUserWorker::class.java, "user")
        source.create(HelloUserWorker::class.java, "user")
        val active = source.getAll(WorkFilter(stateIn = WorkState.Active)).first()
        Assert.assertEquals(3, active.size)
    }

    @Test
    fun `when proceed group work`() = performTest {
        val users = (1..100).map { "user-$it" }
        val flow = source.create(
            HelloUserGroupWorker::class.java,
            HelloUserGroupWorker.Input(users)
        )
        val results = flow.execute()!!.list
        Assert.assertEquals(users.size, results.size)
        results.forEachIndexed { index, s ->
            Assert.assertTrue(s!!.startsWith("Hello ${users[index]}"))
        }
        flow.getChildren().forEachIndexed { index, child ->
            Assert.assertEquals(users[index], child.getInput())
        }
    }

    @Test
    fun `when proceed group work with child fails`() = performTest {
        val users = listOf(
            "Alex",
            HelloUserWorker.ERROR_NAME,
            "Bob",
            HelloUserWorker.ERROR_NAME
        )
        val flow = source.create(
            HelloUserGroupWorker::class.java,
            HelloUserGroupWorker.Input(users)
        )
        val results = flow.execute()!!.list
        Assert.assertEquals(users.size, results.size)
        Assert.assertNull(results[1])
        Assert.assertNull(results[3])
    }

    @Test
    fun `when pause group work and resume it after`() = performTest {
        val users = listOf(
            "Alex",
            "Bob"
        )
        val flow = source.create(
            HelloUserGroupWorker::class.java,
            HelloUserGroupWorker.Input(users)
        )
        repeatWhile(block = flow::getChildren) { all -> all.size != users.size }

        // pause
        source.update(flow, WorkState.Paused, null)
        flow.getChildren().forEach { child ->
            val state = child.getState()
            Assert.assertEquals(WorkState.Paused, state)
        }

        // resume
        source.update(flow, WorkState.Resumed, null)
        repeatWhile(block = flow::getChildren) { it.all { w -> w.getState() != WorkState.Processing } }
        flow.getChildren().forEach { child ->
            val state = child.getState()
            Assert.assertEquals(WorkState.Processing, state)
        }
        Assert.assertEquals(users.size, flow.execute()!!.list.size)
    }

    @Test
    fun `when pause group child and resume it after`() = performTest {
        val users = listOf(
            "Alex",
            "Bob"
        )
        val flow = source.create(
            HelloUserGroupWorker::class.java,
            HelloUserGroupWorker.Input(users)
        )
        repeatWhile(block = flow::getChildren) { all -> all.size != users.size }
        val children = flow.getChildren()

        // pause
        repeatWhile(block = flow::getChildren) { all -> all.any { it.getState() != WorkState.Processing } }
        source.update(children[0], WorkState.Paused, null)

        repeatWhile(block = flow::getChildren) { all -> all[1].getState() != WorkState.Completed }
        Assert.assertTrue(!flow.getState().terminal)

        // resume
        source.update(children[0], WorkState.Resumed, null)
        Assert.assertNotNull(flow.awaitOutput())
        Assert.assertEquals(WorkState.Completed, flow.getState())
    }

    @Test
    fun `when pause group child and resume it via parent`() = performTest {
        val users = listOf(
            "Alex",
            "Bob"
        )
        val flow = source.create(
            HelloUserGroupWorker::class.java,
            HelloUserGroupWorker.Input(users)
        )
        repeatWhile(block = flow::getChildren) { all -> all.size != users.size }
        Logger.debug("stage :: {}", "1")

        // pause
        val children = flow.getChildren()
        repeatWhile(block = flow::getChildren) { all -> all.any {
            Logger.debug("stage :: 1 - state={}", it.getState())
            it.getState() != WorkState.Processing }
        }
        source.update(children[0], WorkState.Paused, null)
        Logger.debug("stage :: {}", "2")

        repeatWhile(block = flow::getChildren) { all ->
            Logger.debug("check state :: {} - {}", all[1].getId(), all[1].getState())
            all[1].getState() != WorkState.Completed
        }
        Assert.assertTrue(!flow.getState().terminal)
        Logger.debug("stage :: {}", "3")

        // resume
        source.update(flow, WorkState.Paused, null)
        Logger.debug("stage :: {}", "4")
        delay(1.seconds)
        source.update(flow, WorkState.Resumed, null)
        Logger.debug("stage :: {}", "5")
        Assert.assertNotNull(flow.awaitOutput())
        Logger.debug("stage :: {}", "6")
        Assert.assertEquals(WorkState.Completed, flow.getState())
    }

    @Test
    fun `when pause group children, resume it after and complete group`() = performTest {
        val users = listOf(
            "Alex",
            "Bob"
        )
        val flow = source.create(
            HelloUserGroupWorker::class.java,
            HelloUserGroupWorker.Input(users)
        )
        val children = flow.getChildren()

        // pause
        repeatWhile(block = flow::getChildren) { all -> all.any { it.getState() != WorkState.Processing } }
        children.forEach { child -> source.update(child, WorkState.Paused, null) }
        delay(1.seconds)
        Assert.assertTrue(!flow.getState().terminal)
        source.update(flow, WorkState.Paused, null)
        delay(1.seconds)

        // resume
        children.forEach { child -> source.update(child, WorkState.Resumed, null) }
        repeatWhile(block = children::toList) { all -> all.any { !it.getState().terminal } }

        source.update(flow, WorkState.Resumed, null)
        Assert.assertNotNull(flow.awaitOutput())
        Assert.assertEquals(WorkState.Completed, flow.getState())
    }

}