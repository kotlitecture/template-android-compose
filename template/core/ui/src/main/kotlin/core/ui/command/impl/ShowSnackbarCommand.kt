package core.ui.command.impl

import core.ui.AppContext
import core.ui.command.Command
import core.ui.command.CommandState
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

    override fun doExecute(commandState: CommandState, appContext: AppContext) {
        val host = appContext.snackbarHostSate
        val scope = appContext.scope
        val job = scope.launch { host.showSnackbar(text) }
        WeakReferenceUtils.replace("ShowSnackbarCommand", job)?.cancel()
    }

}