package core.ui.command.impl

import android.widget.Toast
import core.ui.AppContext
import core.ui.command.Command
import core.ui.command.CommandState
import core.ui.misc.utils.WeakReferenceUtils

class ShowToastCommand(
    private val text: String
) : Command() {

    override fun doExecute(commandState: CommandState, appContext: AppContext) {
        val context = appContext.context.applicationContext
        val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        WeakReferenceUtils.replace(javaClass, toast)?.cancel()
        toast.show()
    }
}