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
    EdgeToEdgeHandler(state)
    SystemDarkModeHandler(state)
    ThemeSwitchHandler(state, content)
}

@Composable
private fun SystemDarkModeHandler(state: ThemeState) {
    val systemDarkMode = isSystemInDarkTheme()
    LaunchedEffect(systemDarkMode) { state.systemDarkModeStore.set(systemDarkMode) }
}

@Composable
private fun EdgeToEdgeHandler(state: ThemeState) {
    val activity = LocalContext.current.findActivity() ?: return
    val data = state.dataStore.asStateValue() ?: return
    DisposableEffect(data) {
        activity.enableEdgeToEdge(
            statusBarStyle = data.systemBarStyle,
            navigationBarStyle = data.navigationBarStyle
        )
        onDispose { }
    }
}

@Composable
private fun ThemeSwitchHandler(state: ThemeState, content: @Composable () -> Unit) {
    val viewModel = provideViewModel<ThemeViewModel>(activityScope = true)
    LaunchedEffect(state) { viewModel.onBind(state) }
    val data = state.dataStore.asStateValue() ?: return
    println("dasdasdasddas $data")
    CompositionLocalProvider(ThemeData.localThemeData provides data) {
        when {
            data is Material3ThemeData -> Material3ThemeProvider(data, content)
            else -> Unit
        }
    }
}