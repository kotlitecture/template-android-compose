package app.feature.webtonative

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy

class WebToNativeDestination : NavigationDestination<WebToNativeDestination.Data>() {

    override val id: String = "webtonative_screen"
    override val dataType: Class<Data> = Data::class.java
    override val strategy: NavigationStrategy = NavigationStrategy.NewInstance
    override fun doRegister(builder: NavGraphBuilder) = screen(builder) { WebToNativeScreen(it!!) }

    data class Data(
        val url: String
    )
}