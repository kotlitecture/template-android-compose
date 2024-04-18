package app.showcases.passcode.navigate.from

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.builtins.serializer

object NavigateWithPasscodeFromDestination : NavigationDestination<Unit>() {

    override val id: String = "navigate_with_passcode_from_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.json(Unit.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { NavigateWithPasscodeFromScreen() }

}