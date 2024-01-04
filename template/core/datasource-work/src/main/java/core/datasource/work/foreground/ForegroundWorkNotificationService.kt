package core.datasource.work.foreground

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import core.datasource.analytics.IAnalyticsSource
import core.datasource.work.IWorkConfig
import core.datasource.work.foreground.notification.DefaultNotificationProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForegroundWorkNotificationService @Inject constructor(
    private val defaultNotificationProvider: DefaultNotificationProvider,
    private val analytics: IAnalyticsSource,
    private val config: IWorkConfig,
    private val app: Application,
) {

    private val manager by lazy { app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    private val channelId: String by lazy {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    config.getChannelName(),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.lockscreenVisibility = NotificationCompat.VISIBILITY_SECRET
                channel.vibrationPattern = longArrayOf(0L)
                channel.enableVibration(true)
                channel.setShowBadge(false)
                manager.createNotificationChannel(channel)
            }
        } catch (e: Exception) {
            analytics.onError("error_create_notification_channel", e)
        }
        CHANNEL_ID
    }

    fun notify(service: Service, info: ForegroundWorkInfo) {
        val provider = defaultNotificationProvider
        val notification = provider.provide(channelId, info)
        service.startForeground(NOTIFICATION_ID, notification)
    }

    fun cancel() {
        manager.cancel(NOTIFICATION_ID)
    }

    companion object {
        const val CHANNEL_ID = "ForegroundWorkNotificationService"
        private const val NOTIFICATION_ID = 1234
    }

}