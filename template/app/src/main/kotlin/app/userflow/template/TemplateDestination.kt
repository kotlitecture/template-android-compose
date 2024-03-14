package app.userflow.template

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination

object TemplateDestination : NavigationDestination<Unit>() {

    override val id: String = "template_screen"
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.json()
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { TemplateScreen() }

}