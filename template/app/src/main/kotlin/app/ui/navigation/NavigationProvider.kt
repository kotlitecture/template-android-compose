package app.ui.navigation

import androidx.compose.runtime.Composable
import app.ui.navigation.left.ModalLeftNavigation

/**
 * Composable function to provide navigation functionality.
 *
 */
@Composable
fun NavigationProvider(content: @Composable () -> Unit) {
    ModalLeftNavigation(content)
}