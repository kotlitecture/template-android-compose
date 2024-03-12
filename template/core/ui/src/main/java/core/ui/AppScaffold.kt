package core.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import core.ui.command.CommandProvider
import core.ui.command.CommandState
import core.ui.navigation.NavigationProvider
import core.ui.navigation.NavigationState
import core.ui.theme.ThemeState
import core.ui.theme.material3.Material3ThemeProvider

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppScaffold(
    navigationState: NavigationState = NavigationState.Default,
    commandState: CommandState = CommandState.Default,
    themeState: ThemeState = ThemeState.Default,
    navGraphBuilder: NavGraphBuilder.() -> Unit,
    bottomBar: @Composable () -> Unit = {},
    overlay: @Composable () -> Unit = {},
    startDestination: String
) {
    val appContext = rememberAppContext()
    CommandProvider(commandState, appContext)
    NavigationProvider(navigationState, appContext)
    Material3ThemeProvider(themeState) {
        Scaffold(
            snackbarHost = { SnackbarHost(appContext.snackbarHostSate) },
            content = {
                NavHost(
                    builder = navGraphBuilder,
                    startDestination = startDestination,
                    navController = appContext.navController,
                    enterTransition = { fadeIn(animationSpec = tween(100)) },
                    exitTransition = { fadeOut(animationSpec = tween(100)) }
                )
            },
            bottomBar = bottomBar
        )
        overlay()
    }
}