package core.datasource.work.foreground.notification

import android.app.Application
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import core.datasource.work.IWorkConfig
import core.datasource.work.extensions.asForegroundWorkIntent
import core.datasource.work.foreground.ForegroundWorkInfo
import java.util.Random
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

abstract class NotificationProvider : INotificationProvider {

    @Inject
    lateinit var app: Application

    @Inject
    lateinit var workConfig: IWorkConfig

    private fun createLaunchIntent(): PendingIntent {
        val launchIntent = app.packageManager.getLaunchIntentForPackage(app.packageName)!!
        launchIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(
            app,
            nextId.incrementAndGet(),
            launchIntent.asForegroundWorkIntent(),
            getPendingIntentFlags()
        )
    }

    private fun getPendingIntentFlags(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
    }

    override fun provide(channelId: String, info: ForegroundWorkInfo): Notification {
        return NotificationCompat.Builder(app, channelId)
            .setContentTitle(info.title)
            .setContentText(info.text)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(workConfig.getNotificationIcon())
            .setContentIntent(createLaunchIntent())
            .apply {
                modify(this, info)
                setSilent(true)
            }
            .setChannelId(channelId)
            .build()
    }

    protected abstract fun modify(builder: NotificationCompat.Builder, info: ForegroundWorkInfo)

    companion object {
        private val nextId = AtomicInteger(Random().nextInt())
    }

}