package core.datasource.work.foreground

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ForegroundWorkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        ForegroundWorkService.start(context)
    }

}