package app.ui.command.impl

import android.content.Intent
import core.ui.AppContext
import app.ui.command.Command
import app.ui.command.CommandState

/**
 * Command for opening the email application.
 *
 * @property title The title of the email application chooser dialog.
 */
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