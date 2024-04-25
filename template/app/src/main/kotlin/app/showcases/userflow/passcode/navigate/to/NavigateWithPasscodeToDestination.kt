package app.showcases.userflow.passcode.navigate.to

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationStrategy
import core.ui.navigation.NavigationDestinationNoArgs

object NavigateWithPasscodeToDestination : NavigationDestinationNoArgs() {

    override val id: String = "navigate_with_passcode_to_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override fun doBind(builder: NavGraphBuilder) =
        composable(builder) { NavigateWithPasscodeToScreen() }

}