package app.feature.webtonative

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WebToNativeDestination : NavigationDestination<WebToNativeDestination.Data>() {

    override val id: String = "webtonative_screen"
    override val strategy: NavigationStrategy = NavigationStrategy.NewInstance
    override fun doRegister(builder: NavGraphBuilder) = screen(builder) { WebToNativeScreen(it!!) }
    override fun toObject(string: String): Data? = Json.decodeFromString(string)
    override fun toString(data: Data): String = Json.encodeToString(data)

    @Serializable
    data class Data(
        val url: String
    )
}