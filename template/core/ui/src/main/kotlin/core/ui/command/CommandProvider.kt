package core.ui.command

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import core.ui.AppContext
import core.ui.provideViewModel

@Composable
fun CommandProvider(commandState: CommandState, appContext: AppContext) {
    val viewModel = provideViewModel<CommandViewModel>(activityScope = true)
    LaunchedEffect(commandState, appContext) { viewModel.onBind(commandState, appContext) }
}