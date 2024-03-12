package app.feature.template

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy

class TemplateDestination : NavigationDestination<Unit>() {

    override val id: String = "template_screen"
    override val dataType: Class<Unit> = Unit.javaClass
    override val strategy: NavigationStrategy = NavigationStrategy.NewInstance
    override fun doRegister(builder: NavGraphBuilder) = screen(builder) { TemplateScreen() }

}