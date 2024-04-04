package app.showcases

import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy

abstract class ShowcaseDestination : NavigationDestination<Unit>() {

    abstract val label: String

    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.memory()

}