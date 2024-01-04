package core.ui.app.command

import core.ui.app.AppContext
import core.ui.misc.extension.copyToClipboard

class CopyTextCommand(
    private val text: String,
    private val toast: String? = null
) : Command() {

    override fun doExecute(appContext: AppContext) {
        val context = appContext.context
        context.copyToClipboard(text) {
            if (toast != null) {
                appContext.appState.onShowToast(toast)
            }
        }
    }
}