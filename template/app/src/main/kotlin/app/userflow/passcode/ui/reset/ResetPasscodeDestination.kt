package app.userflow.passcode.ui.reset

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationStrategy
import core.ui.navigation.NavigationDestinationNoArgs

/**
 * Represents the destination for the reset passcode screen.
 */
object ResetPasscodeDestination : NavigationDestinationNoArgs() {

    override val id: String = "reset_passcode_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override fun doBind(builder: NavGraphBuilder) = dialog(builder) { ResetPasscodeScreen() }

}