package app.feature.webtonative

import androidx.navigation.NavGraphBuilder
import core.ui.app.navigation.Destination
import core.ui.app.navigation.Strategy

class WebToNativeDestination : Destination<WebToNativeDestination.Data>() {
    override fun getId(): String = "webtonative_screen"
    override val dataType: Class<Data> = Data::class.java
    override val strategy: Strategy = Strategy.NewInstance
    override fun doRegister(builder: NavGraphBuilder) = screen(builder) { WebToNativeScreen(it!!) }

    data class Data(
        val url: String
    )
}