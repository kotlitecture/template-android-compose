package app.userflow.theme.change

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationStrategy
import core.ui.navigation.NavigationDestinationNoArgs

/**
 * Navigation destination for the change theme dialog screen.
 */
object ChangeThemeDialogDestination : NavigationDestinationNoArgs() {

    override val id: String = "change_theme_dialog_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override fun doBind(builder: NavGraphBuilder) = dialog(builder) { ChangeThemeDialog() }

}