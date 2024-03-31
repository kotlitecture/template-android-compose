package app.ui.command.impl

import android.widget.Toast
import core.ui.navigation.NavigationContext
import app.ui.command.Command
import app.ui.command.CommandState
import core.ui.misc.utils.WeakReferenceUtils

/**
 * Command for showing a Toast.
 *
 * @property text The text to display in the Toast.
 */
class ShowToastCommand(
    private val text: String
) : Command() {

    override fun doExecute(commandState: CommandState, navigationContext: NavigationContext) {
        val context = navigationContext.context.applicationContext
        val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        WeakReferenceUtils.replace("ShowToastCommand", toast)?.cancel()
        toast.show()
    }
}