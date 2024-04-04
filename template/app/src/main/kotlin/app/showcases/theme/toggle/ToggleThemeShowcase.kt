package app.showcases.theme.toggle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import app.showcases.Showcase
import app.showcases.ShowcasesViewModel
import app.userflow.theme.toggle.ToggleThemeButton
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import core.ui.theme.ThemeData

object ToggleThemeShowcase : Showcase {

    override val label: String = "ToggleThemeButton"

    override fun onClick(viewModel: ShowcasesViewModel) {
        viewModel.navigationState.onNext(ToggleThemeDestination)
    }

    override fun destinations(): List<NavigationDestination<*>> = listOf(
        ToggleThemeDestination
    )

}

object ToggleThemeDestination : NavigationDestination<Unit>() {
    override val id: String = "toggle_theme_dialog"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.memory()
    override fun doBind(builder: NavGraphBuilder) = dialog(builder) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(ThemeData.current.primary)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            ToggleThemeButton()
        }
    }

}