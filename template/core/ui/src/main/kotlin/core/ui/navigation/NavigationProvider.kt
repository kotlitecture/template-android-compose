package core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import core.ui.AppContext
import core.ui.provideViewModel
import core.ui.state.ErrorStateProvider

/**
 * Composable function responsible for providing navigation functionality to the UI.
 *
 * @param navigationState The navigation state containing destination and navigation information.
 * @param appContext The application context containing navigation controller and other related components.
 */
@Composable
fun NavigationProvider(navigationState: NavigationState, appContext: AppContext) {
    val viewModel = provideViewModel<NavigationViewModel>()
    DisposableEffect(navigationState, appContext) {
        viewModel.onBind(navigationState, appContext)
        onDispose { }
    }
    ErrorStateProvider(navigationState)
}