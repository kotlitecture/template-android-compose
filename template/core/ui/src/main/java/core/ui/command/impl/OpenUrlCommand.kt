package core.ui.command.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import core.ui.AppContext
import core.ui.command.Command
import core.ui.command.CommandState
import org.tinylog.Logger

class OpenUrlCommand(
    private val url: String,
    private val title: String? = null,
    private val external: Boolean = false
) : Command() {

    override fun doExecute(commandState: CommandState, appContext: AppContext) {
        val context = appContext.context
        if (external) {
            openUrl(context, url, title)
        } else {
            openUrlInChromeTab(context, url)
        }
    }

    private fun openUrl(context: Context, url: String, title: String? = null) {
        val intent = Intent(Intent.ACTION_VIEW)
            .setData(Uri.parse(url))
        val chooserIntent = Intent.createChooser(intent, title.orEmpty())
        context.startActivity(chooserIntent)
    }

    private fun openUrlInChromeTab(context: Context, url: String) {
        try {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        } catch (e: Exception) {
            Logger.error(e, "openUrlInChromeTab")
            openUrl(context, url)
        }
    }
}