package app.showcases.datasource.http.basic

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationStrategy
import core.ui.navigation.NavigationDestinationNoArgs

object BasicHttpDestination : NavigationDestinationNoArgs() {

    override val id: String = "basic_http_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { BasicHttpScreen() }

}