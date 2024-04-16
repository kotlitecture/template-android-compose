package app.userflow.passcode.ui.unlock

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.Serializable

object UnlockPasscodeDestination : NavigationDestination<UnlockPasscodeDestination.Data>() {

    override val id: String = "unlock_passcode_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Data> = ArgsStrategy.json(Data.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { UnlockPasscodeScreen(it ?: Data()) }

    @Serializable
    data class Data(
        val nextRoute: String? = null,
        val title: String? = null,
        val soft:Boolean = false
    )
}