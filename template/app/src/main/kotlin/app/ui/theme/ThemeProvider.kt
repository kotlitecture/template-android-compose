package app.ui.theme

import androidx.compose.runtime.Composable
import app.provideHiltViewModel

@Composable
fun ThemeProvider(content: @Composable () -> Unit) {
    val viewModel: ThemeViewModel = provideHiltViewModel()
    core.ui.theme.ThemeProvider(viewModel.themeState, content)
}