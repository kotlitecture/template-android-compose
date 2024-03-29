package app.ui.command.impl

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import core.ui.AppContext
import app.ui.command.Command
import app.ui.command.CommandState

/**
 * Command for copying text to the clipboard.
 *
 * @property text The text to be copied.
 * @property label The label associated with the copied text.
 * @property toast The toast message to display after copying.
 */
class CopyTextCommand(
    private val text: String,
    private val label: String? = null,
    private val toast: String? = null
) : Command() {

    override fun doExecute(commandState: CommandState, appContext: AppContext) {
        val context = appContext.context
        val service = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label.orEmpty(), text)
        service.setPrimaryClip(clip)
        toast?.let { ShowToastCommand(it) }?.let(commandState::onCommand)
    }
}