package app.showcases

import androidx.navigation.NavGraphBuilder
import app.showcases.theme.change.ChangeThemeDialogShowcase
import app.showcases.theme.change.ChangeThemeShowcase
import app.showcases.theme.toggle.ToggleThemeShowcase
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy

/**
 * Navigation destination representing the flow for showcasing various features.
 */
object ShowcasesDestination : NavigationDestination<Unit>() {

    val showcases = listOf(
        ChangeThemeShowcase,
        ChangeThemeDialogShowcase,
        ToggleThemeShowcase
    )

    override val id: String = "showcases_flow"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.memory()
    override fun doBind(builder: NavGraphBuilder) {
        composable(builder) { ShowcasesScreen() }
        showcases
            .map { it.destinations() }
            .flatten()
            .onEach { it.bind(builder) }
    }

}