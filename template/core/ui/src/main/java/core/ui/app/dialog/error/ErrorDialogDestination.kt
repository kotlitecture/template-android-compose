package core.ui.app.dialog.error

import androidx.navigation.NavGraphBuilder
import core.ui.app.navigation.Destination
import core.ui.app.navigation.Strategy

class ErrorDialogDestination : Destination<ErrorDialogDestination.Data>() {

    override fun getId(): String = "error_dialog_screen"
    override val dataType: Class<Data> = Data::class.java
    override val strategy: Strategy = Strategy.SingleInstance
    override fun doRegister(builder: NavGraphBuilder) = dialog(builder) { ErrorDialogScreen(it!!) }

    data class Data(
        val title: String? = null,
        val message: String,
        val actionLabel: String? = null
    )
}