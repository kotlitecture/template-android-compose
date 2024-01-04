package core.datasource.work.foreground.notification

import android.app.Notification
import core.datasource.work.foreground.ForegroundWorkInfo

interface INotificationProvider {

    fun provide(channelId: String, info: ForegroundWorkInfo): Notification

}