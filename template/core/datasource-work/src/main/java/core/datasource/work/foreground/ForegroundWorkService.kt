package core.datasource.work.foreground

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.content.ContextCompat
import core.datasource.work.IWorkConfig
import core.datasource.work.IWorkSource
import core.datasource.work.flow.IWorkFlow
import core.datasource.work.model.WorkFilter
import core.datasource.work.model.WorkState
import core.essentials.misc.extensions.infiniteFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import org.tinylog.Logger
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class ForegroundWorkService : Service() {

    @Inject
    lateinit var workSource: IWorkSource

    @Inject
    lateinit var workConfig: IWorkConfig

    @Inject
    lateinit var notificationService: ForegroundWorkNotificationService

    private val scope = GlobalScope
    private val started = AtomicBoolean(false)
    private val jobs = ConcurrentHashMap<JobId, Job>()

    override fun onCreate() {
        super.onCreate()
        registerService()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterService()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerService()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun registerService() {
        runCatching {
            if (started.compareAndSet(false, true)) {
                Logger.debug("registerService :: {}", this)
                startQueueCleaner()
                startQueueListener()
                updateState()
            }
        }
    }

    private fun unregisterService() {
        runCatching {
            if (started.compareAndSet(true, false)) {
                Logger.debug("unregisterService :: {}", this)
                jobs.forEach { it.value.cancel() }
                notificationService.cancel()
                stopForeground(true)
                stopSelf()
            }
        }
    }

    private fun startQueueCleaner() {
        showNotification(workConfig.getNotificationTitle(0), 0)
        launch(JobId.queueCleaner) {
            val interval = workConfig.getQueueCleanerDelay()
            infiniteFlow<Unit>(JobId.queueCleaner.id) {
                getPendingWorks().forEach { work -> execute(work) }
                updateState()
                interval
            }.collect {}
        }
    }

    private suspend fun getPendingWorks(): List<IWorkFlow<*, *>> {
        return workSource
            .getAll(WorkFilter(stateIn = WorkState.Pending, includeChildren = true))
            .firstOrNull()
            ?: emptyList()
    }

    private fun startQueueListener() {
        launch(JobId.queueListener) {
            workSource.queue().collect { work -> execute(work) }
        }
    }

    private fun updateState() {
        launch(JobId.stateUpdater) {
            delay(300.milliseconds)
            val works = jobs.filter { it.key.isWork }
            val size = works.count { it.value.isActive }
            val text = workConfig.getNotificationTitle(size)
            showNotification(text, size)
            if (size == 0) {
                val seconds = workConfig.getQueueCleanerDestroySeconds()
                delay(seconds.seconds)
                if (getPendingWorks().isEmpty()) {
                    unregisterService()
                }
            }
        }
    }

    private suspend fun execute(work: IWorkFlow<*, *>) {
        val id = JobId(work.getId(), isWork = true, force = false)
        launch(id) {
            val workState = work.getState()
            if (!WorkState.Pending.contains(workState)) {
                Logger.debug("cancel work :: {} -> {}", id, work.getState())
                jobs[id]?.cancel()
                work.cancel(workState)
                updateState()
            } else {
                updateState()
                Logger.debug("startWork :: started :: id={}", work.getId())
                val result = work.execute()
                updateState()
                Logger.debug("startWork :: completed :: id={}, result={}", work.getId(), result)
            }
        }
    }

    private fun showNotification(title: String, activeWorks: Int) {
        val text = workConfig.getNotificationText(activeWorks)
        notificationService.notify(this, ForegroundWorkInfo(title, text))
    }

    private fun launch(id: JobId, block: suspend CoroutineScope.() -> Unit) {
        val active = jobs[id]
        Logger.debug("launch :: {} - {}", id, active)
        if (id.force || active == null || !active.isActive) {
            active?.cancel()
            val job = scope.async { runCatching { block.invoke(this) } }
            job.invokeOnCompletion { jobs.remove(id, job) }
            jobs[id] = job
        }
    }

    data class JobId(
        val id: String,
        val isWork: Boolean,
        val force: Boolean = false
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is JobId) return false

            if (id != other.id) return false

            return true
        }

        override fun hashCode(): Int {
            return id.hashCode()
        }

        companion object {
            val stateUpdater = JobId("update_state", false, force = true)
            val queueListener = JobId("queue_listener", false)
            val queueCleaner = JobId("queue_cleaner", false)
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ForegroundWorkService::class.java)
            ContextCompat.startForegroundService(context, intent)
            ForegroundWorkAwakeWorker.start(context)
        }
    }

}