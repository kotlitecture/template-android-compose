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
import app.ui.component.AnyIcon
import app.ui.navigation.NavigationBarState

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
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        NavigationBar {
            val selected = state.activePageStore.asStateValue()
            pages.forEach { page ->
                NavigationBarItem(
                    alwaysShowLabel = page.alwaysShowLabel,
                    selected = page.model == selected?.model,
                    onClick = page.onClick,
                    icon = { AnyIcon(model = page.icon) },
                    label = { page.label?.let { Text(text = it) } }
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