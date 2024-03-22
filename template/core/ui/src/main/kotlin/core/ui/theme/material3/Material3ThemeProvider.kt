package core.ui.theme.material3

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import core.ui.provideViewModel
import core.ui.theme.ThemeData
import core.ui.theme.ThemeState

/**
 * Provides a Material3 theme based on the provided theme state.
 *
 * @param themeState The state of the Material3 theme.
 * @param content The composable content to be wrapped with the Material3 theme.
 */
@Composable
fun Material3ThemeProvider(
    themeState: ThemeState,
    content: @Composable () -> Unit
) {
    val viewModel = provideViewModel<Material3ThemeViewModel>(activityScope = true)
    LaunchedEffect(themeState) { viewModel.onBind(themeState) }
    val themeData = themeState.dataStore.asStateValue() as? Material3ThemeData ?: return
    CompositionLocalProvider(ThemeData.localThemeData provides themeData) {
        MaterialTheme(
            colorScheme = themeData.colorScheme,
            typography = themeData.typography,
            content = content
        )
    }
}