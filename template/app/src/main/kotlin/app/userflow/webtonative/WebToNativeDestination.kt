package app.userflow.webtonative

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination

object WebToNativeDestination : NavigationDestination<WebToNativeDestination.Data>() {

    override val id: String = "webtonative_screen"
    override val argsStrategy: ArgsStrategy<Data> = ArgsStrategy.json()
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { WebToNativeScreen(it!!) }

    data class Data(
        val url: String
    )
}