@file:OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)

package app.ui.navigation.adaptive

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import app.provideHiltViewModel
import app.ui.component.AnyIcon
import app.ui.navigation.NavigationBarViewModel

/**
 * Composable function to display an adaptive navigation.
 *
 * @param content The content to display.
 */
@Composable
fun AdaptiveNavigation(content: @Composable () -> Unit) {
    val viewModel: NavigationBarViewModel = provideHiltViewModel()
    val pages = viewModel.availablePagesStore.asStateValue()?.takeIf { it.isNotEmpty() } ?: return
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            val selected = viewModel.activePageStore.asStateValue()
            pages.forEach { page ->
                item(
                    label = { page.getLabel()?.let { Text(text = it) } },
                    icon = { AnyIcon(model = page.getIcon()) },
                    selected = page.id == selected?.id,
                    onClick = page.onClick,
                )
            }
        },
        content = content
    )
}