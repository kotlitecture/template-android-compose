package core.datasource.work.extensions

import android.content.Intent
import core.datasource.work.foreground.ForegroundWorkNotificationService

fun Intent.isForegroundWorkIntent(): Boolean {
    return getBooleanExtra(ForegroundWorkNotificationService.CHANNEL_ID, false)
}

fun Intent.asForegroundWorkIntent(): Intent {
    putExtra(ForegroundWorkNotificationService.CHANNEL_ID, true)
    return this
}