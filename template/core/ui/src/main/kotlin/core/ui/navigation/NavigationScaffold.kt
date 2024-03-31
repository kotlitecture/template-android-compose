package core.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable

/**
 * Composable function to display the main scaffold of the app.
 *
 * @param navigationContext The context of the app.
 * @param navigationState The state of the navigation.
 * @param topBar The composable function to display the top bar.
 * @param bottomBar The composable function to display the bottom bar.
 * @param floatingActionButton The composable function to display the floating action button.
 * @param floatingActionButtonPosition The position of the floating action button.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppScaffold(
    navigationContext: NavigationContext,
    navigationState: NavigationState,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End
) {
    val startDestination = navigationState.startDestinationStore.asStateValue() ?: return
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        snackbarHost = { SnackbarHost(navigationContext.snackbarHostSate) },
        content = {
            NavigationHost(
                navigationContext = navigationContext,
                navigationState = navigationState,
                startDestination = startDestination
            )
        }
    )
}