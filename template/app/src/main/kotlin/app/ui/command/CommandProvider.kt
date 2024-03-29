package app.ui.command

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import core.ui.AppContext
import core.ui.provideViewModel

/**
 * Provides functionality for handling commands within a Composable.
 *
 * @param appContext The application context.
 */
@Composable
fun CommandProvider(appContext: AppContext) {
    val viewModel = provideViewModel<CommandViewModel>(activityScope = true)
    DisposableEffect(appContext) {
        viewModel.onBind(appContext)
        onDispose { }
    }
}