package app.userflow.navigation.c

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationStrategy
import core.ui.navigation.NavigationDestinationNoArgs

object NavigationCDestination : NavigationDestinationNoArgs() {

    override val id: String = "navigation_c_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { NavigationCScreen() }

}