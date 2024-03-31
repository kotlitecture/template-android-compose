package app.userflow.webtonative

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.Serializable

object WebToNativeDestination : NavigationDestination<WebToNativeDestination.Data>() {

    override val id: String = "web_to_native_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override val argsStrategy: ArgsStrategy<Data> = ArgsStrategy.json(Data.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { WebToNativeScreen(it) }

    @Serializable
    class Data(
        val url: String
    )
}