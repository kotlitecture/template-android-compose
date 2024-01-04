package core.ui.app.command

import android.content.Intent
import core.ui.app.AppContext
import core.ui.misc.extension.safeIntent

class OpenEmailCommand(
    private val title: String? = null
) : Command() {

    override fun doExecute(appContext: AppContext) {
        val context = appContext.context
        val intent = Intent(Intent.ACTION_MAIN)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addCategory(Intent.CATEGORY_APP_EMAIL)
        val chooserIntent = Intent.createChooser(intent, title.orEmpty())
        context.safeIntent(chooserIntent)
    }
}