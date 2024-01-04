package core.datasource.work.foreground.notification

import androidx.core.app.NotificationCompat
import core.datasource.work.foreground.ForegroundWorkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultNotificationProvider @Inject constructor() : NotificationProvider() {

    override fun modify(builder: NotificationCompat.Builder, info: ForegroundWorkInfo) = Unit

}