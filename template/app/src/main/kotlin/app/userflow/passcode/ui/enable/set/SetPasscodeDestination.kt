package app.userflow.passcode.ui.enable.set

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy

/**
 * Represents the destination for the set passcode screen.
 */
object SetPasscodeDestination : NavigationDestination<Unit>() {

    override val id: String = "set_passcode_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.noArgs()
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { SetPasscodeScreen() }

}