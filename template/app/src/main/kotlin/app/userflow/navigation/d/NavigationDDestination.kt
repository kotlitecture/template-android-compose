package app.userflow.navigation.d

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.Serializable

object NavigationDDestination : NavigationDestination<NavigationDDestination.Data>() {

    override val id: String = "navigation_d_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Data> = ArgsStrategy.json(Data.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { NavigationDScreen(it) }

    @Serializable
    data class Data(
        val title: String
    )
}