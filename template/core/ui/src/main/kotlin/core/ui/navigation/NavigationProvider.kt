package core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import core.ui.AppContext
import core.ui.provideViewModel
import core.ui.state.ErrorStateProvider

@Composable
fun NavigationProvider(navigationState: NavigationState, appContext: AppContext) {
    val viewModel = provideViewModel<NavigationViewModel>(activityScope = true)
    LaunchedEffect(navigationState, appContext) { viewModel.onBind(navigationState, appContext) }
    ErrorStateProvider(navigationState.dataStateStore)
}