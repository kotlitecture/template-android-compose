package core.ui.app.command

import core.essentials.misc.utils.WeakReferenceUtils
import core.ui.app.AppContext
import kotlinx.coroutines.launch

class ShowSnackbarCommand(
    private val text: String
) : Command() {

    override fun doExecute(appContext: AppContext) {
        val host = appContext.snackbarHostSate
        val scope = appContext.scope
        val job = scope.launch { host.showSnackbar(text) }
        WeakReferenceUtils.replace(javaClass, job)?.cancel()
    }

}