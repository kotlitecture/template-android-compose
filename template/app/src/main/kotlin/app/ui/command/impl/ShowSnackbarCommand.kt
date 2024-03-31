package app.ui.command.impl

import core.ui.navigation.NavigationContext
import app.ui.command.Command
import app.ui.command.CommandState
import core.ui.misc.utils.WeakReferenceUtils
import kotlinx.coroutines.launch

/**
 * Command for showing a Snackbar.
 *
 * @property text The text to display in the Snackbar.
 */
class ShowSnackbarCommand(
    private val text: String
) : Command() {

    override fun doExecute(commandState: CommandState, navigationContext: NavigationContext) {
        val host = navigationContext.snackbarHostSate
        val scope = navigationContext.scope
        val job = scope.launch { host.showSnackbar(text) }
        WeakReferenceUtils.replace("ShowSnackbarCommand", job)?.cancel()
    }

}