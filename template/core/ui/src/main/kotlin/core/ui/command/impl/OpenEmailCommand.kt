package core.ui.command.impl

import android.content.Intent
import core.ui.AppContext
import core.ui.command.Command
import core.ui.command.CommandState

class OpenEmailCommand(
    private val title: String? = null
) : Command() {

    override fun doExecute(commandState: CommandState, appContext: AppContext) {
        val context = appContext.context
        val intent = Intent(Intent.ACTION_MAIN)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addCategory(Intent.CATEGORY_APP_EMAIL)
        val chooserIntent = Intent.createChooser(intent, title.orEmpty())
        context.startActivity(chooserIntent)
    }
}