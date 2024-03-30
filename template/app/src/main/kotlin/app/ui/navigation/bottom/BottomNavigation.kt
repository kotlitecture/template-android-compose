package app.ui.navigation.bottom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import app.provideHiltViewModel
import app.ui.component.AnyIcon
import app.ui.navigation.NavigationBarPage
import app.ui.navigation.NavigationBarViewModel
import core.ui.state.StoreObject

/**
 * Composable function responsible for rendering the bottom navigation bar.
 */
@Composable
fun BottomNavigation() {
    val viewModel: NavigationBarViewModel = provideHiltViewModel()
    val pages = viewModel.availablePagesStore.asStateValue()?.takeIf { it.isNotEmpty() } ?: return
    val visibilityState = remember { MutableTransitionState(false) }
    VisibilityHandler(viewModel.activePageStore, visibilityState)
    AnimatedVisibility(
        visibleState = visibilityState,
        enter = slideInVertically { it / 2 },
        exit = slideOutVertically { it / 2 }
    ) {
        NavigationBar {
            val selected = viewModel.activePageStore.asStateValue()
            pages.forEach { page ->
                NavigationBarItem(
                    label = { page.getLabel()?.let { Text(text = it) } },
                    alwaysShowLabel = page.alwaysShowLabel,
                    icon = { AnyIcon(model = page.getIcon()) },
                    selected = page.id == selected?.id,
                    onClick = page.onClick,
                )
            }
        }
    }
}

@Composable
private fun VisibilityHandler(
    activePageStore: StoreObject<NavigationBarPage>,
    visibleState: MutableTransitionState<Boolean>
) {
    val visible = activePageStore.asStateValue() != null
    LaunchedEffect(visible) {
        visibleState.targetState = visible
    }
}