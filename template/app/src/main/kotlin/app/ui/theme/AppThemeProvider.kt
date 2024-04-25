package app.ui.theme

import androidx.compose.runtime.Composable
import app.appViewModel
import core.ui.theme.ThemeProvider

/**
 * Composable function to provide the theme for the application.
 *
 * @param content The composable content to be themed.
 */
@Composable
fun AppThemeProvider(content: @Composable () -> Unit) {
    val viewModel: AppThemePersistenceViewModel = appViewModel()
    ThemeProvider(viewModel.themeState, content)
}