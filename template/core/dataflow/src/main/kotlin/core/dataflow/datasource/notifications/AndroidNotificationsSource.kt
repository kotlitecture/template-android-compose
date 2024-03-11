package core.dataflow.datasource.notifications

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import androidx.core.app.NotificationManagerCompat
import org.tinylog.Logger

class AndroidNotificationsSource(private val app: Application) : NotificationsSource {

    override suspend fun isEnabled(): Boolean {
        return try {
            NotificationManagerCompat.from(app).areNotificationsEnabled()
        } catch (e: Exception) {
            Logger.error(e, "isEnabled")
            false
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override suspend fun enable() {
        val intent = Intent()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, app.packageName)
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("app_package", app.packageName)
                intent.putExtra("app_uid", app.applicationInfo.uid)
            }

            else -> {
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("package:$app.packageName")
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        app.startActivity(intent)
    }

    override suspend fun isEnabled(channelId: String): Boolean {
        try {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!TextUtils.isEmpty(channelId)) {
                    val manager =
                        app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val channel = manager.getNotificationChannel(channelId)
                    return channel.importance != NotificationManager.IMPORTANCE_NONE
                }
                false
            } else {
                NotificationManagerCompat.from(app).areNotificationsEnabled()
            }
        } catch (e: Exception) {
            Logger.error(e, "isEnabled channel :: {}", channelId)
            return false
        }
    }

    override suspend fun enable(channelId: String) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!TextUtils.isEmpty(channelId)) {
                    val manager =
                        app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val channel = manager.getNotificationChannel(channelId)
                    channel.importance = NotificationManager.IMPORTANCE_DEFAULT
                }
            }
        } catch (e: Exception) {
            Logger.error(e, "enable channel :: {}", channelId)
        }
    }

    override suspend fun disable(channelId: String) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!TextUtils.isEmpty(channelId)) {
                    val manager =
                        app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val channel = manager.getNotificationChannel(channelId)
                    channel.importance = NotificationManager.IMPORTANCE_NONE
                }
            }
        } catch (e: Exception) {
            Logger.error(e, "disable channel :: {}", channelId)
        }
    }

}