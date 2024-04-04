package app.ui.command

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import core.ui.navigation.NavigationContext
import core.ui.provideViewModel

/**
 * Provides functionality for handling commands within a Composable.
 *
 * @param navigationContext The application context.
 */
@Composable
fun CommandProvider(navigationContext: NavigationContext) {
    val viewModel = provideViewModel<CommandViewModel>(activityScope = true)
    LaunchedEffect(navigationContext) {
        viewModel.onBind(navigationContext)
    }
}