package core.ui.command.impl

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import core.ui.AppContext
import core.ui.command.Command
import core.ui.command.CommandState

class CopyTextCommand(
    private val text: String,
    private val label: String? = null,
    private val toast: String? = null
) : Command() {

    override fun doExecute(commandState:CommandState, appContext: AppContext) {
        val context = appContext.context
        val service = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label.orEmpty(), text)
        service.setPrimaryClip(clip)
        toast?.let { ShowToastCommand(it) }?.let(commandState::onCommand)
    }
}