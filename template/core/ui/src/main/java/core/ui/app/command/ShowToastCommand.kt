package core.ui.app.command

import android.widget.Toast
import core.essentials.misc.utils.WeakReferenceUtils
import core.ui.app.AppContext

class ShowToastCommand(
    private val text: String
) : Command() {

    override fun doExecute(appContext: AppContext) {
        val context = appContext.context.applicationContext
        val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        WeakReferenceUtils.replace(javaClass, toast)?.cancel()
        toast.show()
    }
}