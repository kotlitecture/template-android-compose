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
import app.ui.navigation.NavigationBarState
import app.ui.navigation.NavigationBarViewModel

/**
 * Composable function responsible for rendering the bottom navigation bar.
 */
@Composable
fun BottomNavigation() {
    val viewModel: NavigationBarViewModel = provideHiltViewModel()
    BottomNavigation(state = viewModel.navigationBarState)
}

/**
 * Composable function responsible for rendering the bottom navigation bar.
 *
 * @param state The navigation bar state containing available pages and active page.
 */
@Composable
fun BottomNavigation(state: NavigationBarState) {
    val pages = state.availablePagesStore.asStateValue()?.takeIf { it.isNotEmpty() } ?: return
    val visibilityState = remember { MutableTransitionState(false) }
    VisibilityHandler(state, visibilityState)
    AnimatedVisibility(
        visibleState = visibilityState,
        enter = slideInVertically { it / 2 },
        exit = slideOutVertically { it / 2 }
    ) {
        NavigationBar {
            val selected = state.activePageStore.asStateValue()
            pages.forEach { page ->
                NavigationBarItem(
                    label = { page.label?.let { Text(text = it) } },
                    alwaysShowLabel = page.alwaysShowLabel,
                    icon = { AnyIcon(model = page.icon) },
                    selected = page.id == selected?.id,
                    onClick = page.onClick,
                )
            }
        }
    }
}

@Composable
private fun VisibilityHandler(state: NavigationBarState, visibleState: MutableTransitionState<Boolean>) {
    val visible = state.activePageStore.asStateValue() != null
    LaunchedEffect(visible) {
        visibleState.targetState = visible
    }
}