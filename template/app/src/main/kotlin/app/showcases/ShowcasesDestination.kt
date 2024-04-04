package app.showcases

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy

/**
 * Navigation destination representing the flow for showcasing various features.
 */
object ShowcasesDestination : NavigationDestination<Unit>() {

    val showcases = listOf<ShowcaseDestination>(

    )

    override val id: String = "showcases_flow"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.memory()

    override fun doBind(builder: NavGraphBuilder) {
        composable(builder) { ShowcasesScreen() }
        bind(builder, *showcases.toTypedArray())
    }

}