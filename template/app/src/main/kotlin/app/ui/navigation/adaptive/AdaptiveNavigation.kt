@file:OptIn(
    ExperimentalMaterial3AdaptiveNavigationSuiteApi::class,
    ExperimentalMaterial3AdaptiveApi::class
)

package app.ui.navigation.adaptive

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import app.provideHiltViewModel
import app.ui.component.AnyIcon
import app.ui.navigation.NavigationBarViewModel
import core.ui.state.StoreObject

/**
 * Composable function to display an adaptive navigation.
 *
 * @param content The content to display.
 */
@Composable
fun AdaptiveNavigation(content: @Composable () -> Unit) {
    val viewModel: NavigationBarViewModel = provideHiltViewModel()
    val pages = viewModel.pagesStore.asStateValue()
    if (pages.isNullOrEmpty()) {
        content()
        return
    }
    NavigationSuiteScaffold(
        layoutType = getLayoutType(viewModel.visibilityStore),
        navigationSuiteItems = {
            val selected = viewModel.selectedPageStore.asStateValue()
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

@Stable
@Composable
private fun getLayoutType(visibilityStore: StoreObject<Boolean>): NavigationSuiteType {
    val visible = visibilityStore.asStateValue() != false
    if (!visible) return NavigationSuiteType.None
    val adaptiveInfo = currentWindowAdaptiveInfo()
    return NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
}