package app.showcases

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationStrategy
import core.ui.navigation.NavigationDestinationNoArgs

/**
 * Navigation destination representing the flow for showcasing various features.
 */
object ShowcasesDestination : NavigationDestinationNoArgs() {

    override val id: String = "showcases_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override fun doBind(builder: NavGraphBuilder) {
        composable(builder) { ShowcasesScreen() }
        Showcases.all
            .filterIsInstance<ShowcaseItem>()
            .map { it.dependsOn() }
            .flatten()
            .onEach { it.bind(builder) }
    }

}