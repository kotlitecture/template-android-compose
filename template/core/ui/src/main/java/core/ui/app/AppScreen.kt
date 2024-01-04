package core.ui.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import core.ui.app.dialog.confirm.ConfirmOverlay
import core.ui.app.loading.LoadingLayout
import core.ui.app.theme.AppTheme
import core.ui.mvvm.provideViewModel
import org.tinylog.Logger

@Composable
fun AppScreen(
    navGraphBuilder: NavGraphBuilder.() -> Unit,
    footer: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit,
    route: String
) {
    val viewModel: AppViewModel = provideViewModel {}
    val context = viewModel.bindContext()
    AppTheme(viewModel.isReady()) {
        LoadingLayout {
            Scaffold(
                snackbarHost = { SnackbarHost(context.snackbarHostSate) },
                content = {
                    Logger.debug("paddings :: {}", it)
                    NavHost(
                        startDestination = route,
                        builder = navGraphBuilder,
                        navController = context.navController,
                        enterTransition = { fadeIn(animationSpec = tween(100)) },
                        exitTransition = { fadeOut(animationSpec = tween(100)) }
                    )
                },
                bottomBar = bottomBar
            )
            footer()
            ConfirmOverlay(viewModel.appState.confirmStore)
        }
    }
}