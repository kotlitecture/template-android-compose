package app.showcases.userflow.passcode.navigate.from

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationStrategy
import core.ui.navigation.NavigationDestinationNoArgs

object NavigateWithPasscodeFromDestination : NavigationDestinationNoArgs() {

    override val id: String = "navigate_with_passcode_from_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { NavigateWithPasscodeFromScreen() }

}