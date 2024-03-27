package core.ui.command

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import core.ui.AppContext
import core.ui.provideViewModel

/**
 * Provides functionality for handling commands within a Composable.
 *
 * @param commandState The state of commands.
 * @param appContext The application context.
 */
@Composable
fun CommandProvider(commandState: CommandState, appContext: AppContext) {
    val viewModel = provideViewModel<CommandViewModel>()
    LaunchedEffect(commandState, appContext) { viewModel.onBind(commandState, appContext) }
}