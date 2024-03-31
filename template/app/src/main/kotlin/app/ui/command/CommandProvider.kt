package app.ui.command

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
    DisposableEffect(navigationContext) {
        viewModel.onBind(navigationContext)
        onDispose { }
    }
}