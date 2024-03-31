package app.ui.command.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import core.ui.navigation.NavigationContext
import app.ui.command.Command
import app.ui.command.CommandState

/**
 * Command for opening a URL.
 *
 * @property url The URL to open.
 * @property title The title of the chooser dialog.
 * @property external Flag indicating whether to open the URL externally or in a Chrome custom tab.
 */
class OpenUrlCommand(
    private val url: String,
    private val title: String? = null,
    private val external: Boolean = false
) : Command() {

    override fun doExecute(commandState: CommandState, navigationContext: NavigationContext) {
        val context = navigationContext.context
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
            openUrl(context, url)
        }
    }
}