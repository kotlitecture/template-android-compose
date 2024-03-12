package core.ui.command.impl

import core.essentials.misc.extensions.isIgnoredException
import core.essentials.misc.utils.WeakReferenceUtils
import core.ui.AppContext
import core.ui.command.Command
import core.ui.command.CommandState
import kotlinx.coroutines.launch

class ShowErrorCommand(
    private val th: Throwable
) : Command() {

    override fun doExecute(commandState: CommandState, appContext: AppContext) {
        if (th.isIgnoredException()) return
        val host = appContext.snackbarHostSate
        val scope = appContext.scope
        val message = th.message ?: th.stackTraceToString()
        val job = scope.launch { host.showSnackbar(message) }
        WeakReferenceUtils.replace(javaClass, job)?.cancel()
    }

}