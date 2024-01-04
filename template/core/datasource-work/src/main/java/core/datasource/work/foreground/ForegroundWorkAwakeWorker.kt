package core.datasource.work.foreground

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class ForegroundWorkAwakeWorker(
    val context: Context,
    workerParams: WorkerParameters
) : Worker(
    context,
    workerParams
) {

    override fun doWork(): Result {
        ForegroundWorkService.start(context)
        return Result.success()
    }

    companion object {
        private const val ID = "ForegroundWorkAwakeWorker"

        fun start(context: Context) {
            val interval = PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
            runCatching {
                val request = PeriodicWorkRequest
                    .Builder(
                        ForegroundWorkAwakeWorker::class.java,
                        interval,
                        TimeUnit.MILLISECONDS
                    )
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                    .setInitialDelay(interval, TimeUnit.MILLISECONDS)
                    .build()
                WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                    ID,
                    ExistingPeriodicWorkPolicy.KEEP,
                    request
                )
            }
        }

        fun stop(context: Context) {
            runCatching {
                WorkManager.getInstance(context).cancelUniqueWork(ID)
            }
        }
    }

}