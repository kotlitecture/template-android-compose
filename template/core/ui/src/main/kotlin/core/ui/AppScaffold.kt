package core.ui

import android.annotation.SuppressLint
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import core.ui.command.CommandProvider
import core.ui.command.CommandState
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationHost
import core.ui.navigation.NavigationState
import core.ui.theme.ThemeProvider
import core.ui.theme.ThemeState

/**
 * Composable function representing the main scaffold of the application, including navigation, snackbar,
 * command handling, and theme management.
 *
 * @param navigationState The state of navigation in the application.
 * @param commandState The state of command handling in the application.
 * @param themeState The state of the application theme.
 * @param destinations The list of navigation destinations in the application.
 * @param startDestination The start destination for navigation.
 * @param bottomBar The composable function for the bottom bar UI.
 * @param overlay The composable function for the overlay UI.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppScaffold(
    navigationState: NavigationState = NavigationState.Default,
    commandState: CommandState = CommandState.Default,
    themeState: ThemeState = ThemeState.Default,
    destinations: List<NavigationDestination<*>>,
    startDestination: NavigationDestination<*>,
    overlay: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End
) {
    ThemeProvider(themeState) {
        val appContext = rememberAppContext()
        Scaffold(
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            snackbarHost = { SnackbarHost(appContext.snackbarHostSate) },
            content = {
                NavigationHost(
                    appContext = appContext,
                    destinations = destinations,
                    navigationState = navigationState,
                    startDestination = startDestination,
                )
            }
        )
        CommandProvider(commandState, appContext)
        overlay()
    }
}