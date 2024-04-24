package app.ui.screen.template_no_args

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.builtins.serializer

object TemplateNoArgsDestination : NavigationDestination<Unit>() {

    override val id: String = "template_no_args_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.json(Unit.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { TemplateNoArgsScreen() }

}