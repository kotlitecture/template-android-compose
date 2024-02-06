package core.ui.app.dialog.hint

import androidx.navigation.NavGraphBuilder
import core.ui.app.navigation.Destination
import core.ui.app.navigation.Strategy

class HintDialogDestination : Destination<HintDialogDestination.Data>() {

    override val savable: Boolean = false
    override val id: String = "hint_dialog_screen"
    override val dataType: Class<Data> = Data::class.java
    override val strategy: Strategy = Strategy.NewInstance
    override fun doRegister(builder: NavGraphBuilder) = dialog(builder) { HintDialogScreen(it) }

    data class Data(
        val title: String? = null,
        val message: String,
        val icon: Any? = null,
        val actionLabel: String? = null,
    )

}