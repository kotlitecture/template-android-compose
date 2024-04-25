package app.showcases.passcode.navigate.to

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy

object NavigateWithPasscodeToDestination : NavigationDestination<Unit>() {

    override val id: String = "navigate_with_passcode_to_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.noArgs()
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { NavigateWithPasscodeToScreen() }

}