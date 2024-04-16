package app.userflow.passcode.ui.enable.confirm

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.Serializable

object ConfirmPasscodeDestination : NavigationDestination<ConfirmPasscodeDestination.Data>() {

    override val id: String = "confirm_passcode_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override val argsStrategy: ArgsStrategy<Data> = ArgsStrategy.json(Data.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { ConfirmPasscodeScreen(it!!) }

    @Serializable
    data class Data(
        val code: String
    )

}