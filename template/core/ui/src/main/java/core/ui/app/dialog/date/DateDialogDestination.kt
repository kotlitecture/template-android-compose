package core.ui.app.dialog.date

import androidx.navigation.NavGraphBuilder
import core.ui.app.navigation.Destination
import core.ui.app.navigation.Strategy

class DateDialogDestination : Destination<Unit>() {

    override val id: String = "date_dialog_screen"
    override val dataType: Class<Unit> = Unit.javaClass
    override val strategy: Strategy = Strategy.SingleInstance
    override fun doRegister(builder: NavGraphBuilder) = dialog(builder) { DateRangeDialogScreen() }
}