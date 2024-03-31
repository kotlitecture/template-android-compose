package app.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import core.ui.state.StoreObject

/**
 * Composable function to provide navigation functionality.
 *
 */
@Composable
fun NavigationBarProvider(content: @Composable () -> Unit) {
    content()
}

@Stable
internal fun getDrawerValue(visibilityStore: StoreObject<Boolean>): DrawerValue {
    return if (visibilityStore.get() == true) DrawerValue.Open else DrawerValue.Closed
}

@Composable
internal fun DrawerVisibilityHandler(
    visibilityStore: StoreObject<Boolean>,
    drawerState: DrawerState
) {
    val visible = visibilityStore.asStateValue() == true
    LaunchedEffect(visible) {
        if (!drawerState.isAnimationRunning) {
            when {
                visible -> drawerState.open()
                else -> drawerState.close()
            }
        }
    }
}