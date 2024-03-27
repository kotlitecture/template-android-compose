package core.ui.theme

import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import core.ui.misc.extensions.findActivity
import core.ui.provideViewModel
import core.ui.theme.material3.Material3ThemeData
import core.ui.theme.material3.Material3ThemeProvider

@Composable
fun ThemeProvider(
    state: ThemeState,
    content: @Composable () -> Unit
) {
    val viewModel = provideViewModel<ThemeViewModel>()
    LaunchedEffect(state) { viewModel.onBind(state) }
    EdgeToEdgeHandler(viewModel)
    SystemDarkModeHandler(state)
    ThemeSwitchHandler(viewModel, content)
}

@Composable
private fun SystemDarkModeHandler(state: ThemeState) {
    val systemDarkMode = isSystemInDarkTheme()
    LaunchedEffect(systemDarkMode) { state.systemDarkModeStore.set(systemDarkMode) }
}

@Composable
private fun EdgeToEdgeHandler(viewModel: ThemeViewModel) {
    val activity = LocalContext.current.findActivity() ?: return
    val data = viewModel.dataStore.asStateValue() ?: return
    DisposableEffect(data) {
        activity.enableEdgeToEdge(
            statusBarStyle = data.systemBarStyle,
            navigationBarStyle = data.navigationBarStyle
        )
        onDispose { }
    }
}

@Composable
private fun ThemeSwitchHandler(viewModel: ThemeViewModel, content: @Composable () -> Unit) {
    val data = viewModel.dataStore.asStateValue() ?: return
    CompositionLocalProvider(ThemeData.localThemeData provides data) {
        when {
            data is Material3ThemeData -> Material3ThemeProvider(data, content)
            else -> content()
        }
    }
}