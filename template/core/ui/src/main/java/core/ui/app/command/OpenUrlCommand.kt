package core.ui.app.command

import core.ui.app.AppContext
import core.ui.misc.extension.openUrl
import core.ui.misc.extension.openUrlInChromeTab

class OpenUrlCommand(
    private val url: String,
    private val title: String? = null,
    private val external: Boolean = false
) : Command() {

    override fun doExecute(appContext: AppContext) {
        val context = appContext.context
        if (external) {
            context.openUrl(url, title.orEmpty())
        } else {
            context.openUrlInChromeTab(url)
        }
    }
}