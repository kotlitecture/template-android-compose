package app.feature.template

import androidx.navigation.NavGraphBuilder
import core.ui.app.navigation.Destination
import core.ui.app.navigation.Strategy

class TemplateDestination : Destination<Unit>() {
    override fun getId(): String = "template_screen"
    override val dataType: Class<Unit> = Unit.javaClass
    override val strategy: Strategy = Strategy.NewInstance
    override fun doRegister(builder: NavGraphBuilder) = screen(builder) { TemplateScreen() }
}