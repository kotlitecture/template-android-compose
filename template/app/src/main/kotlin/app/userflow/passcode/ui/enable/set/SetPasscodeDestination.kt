package app.userflow.passcode.ui.enable.set

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationStrategy
import core.ui.navigation.NavigationDestinationNoArgs

/**
 * Represents the destination for the set passcode screen.
 */
object SetPasscodeDestination : NavigationDestinationNoArgs() {

    override val id: String = "set_passcode_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { SetPasscodeScreen() }

}