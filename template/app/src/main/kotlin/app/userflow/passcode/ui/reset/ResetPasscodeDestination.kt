package app.userflow.passcode.ui.reset

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.builtins.serializer

/**
 * Represents the destination for the reset passcode screen.
 */
object ResetPasscodeDestination : NavigationDestination<Unit>() {

    override val id: String = "reset_passcode_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.json(Unit.serializer())
    override fun doBind(builder: NavGraphBuilder) = dialog(builder) { ResetPasscodeScreen() }

}