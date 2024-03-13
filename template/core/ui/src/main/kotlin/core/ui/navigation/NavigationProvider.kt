package core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import core.ui.AppContext
import core.ui.provideViewModel

@Composable
fun NavigationProvider(navigationState: NavigationState, appContext: AppContext) {
    val viewModel = provideViewModel<NavigationViewModel>(activityScope = true)
    LaunchedEffect(navigationState, appContext) { viewModel.onBind(navigationState, appContext) }
}