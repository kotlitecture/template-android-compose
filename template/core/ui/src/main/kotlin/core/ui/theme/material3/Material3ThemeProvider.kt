package core.ui.theme.material3

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import core.ui.provideViewModel
import core.ui.theme.ThemeData
import core.ui.theme.ThemeState

@Composable
fun Material3ThemeProvider(
    themeState: ThemeState,
    content: @Composable () -> Unit
) {
    val viewModel = provideViewModel<Material3ThemeViewModel>(activityScope = true)
    LaunchedEffect(themeState) { viewModel.onBind(themeState) }
    val themeData = viewModel.themeDataStore.asStateValue() ?: return
    CompositionLocalProvider(ThemeData.localThemeData provides themeData) {
        MaterialTheme(
            colorScheme = themeData.colorScheme,
            typography = themeData.typography,
            content = content
        )
    }
}