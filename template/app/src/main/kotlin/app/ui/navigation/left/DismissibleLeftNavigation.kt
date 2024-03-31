package app.ui.navigation.left

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import app.provideHiltViewModel
import app.ui.component.AnyIcon
import app.ui.navigation.NavigationBarViewModel
import core.ui.state.StoreObject

/**
 * Composable function to display a dismissible left navigation.
 *
 * @param content The content to display.
 */
@Composable
fun DismissibleLeftNavigation(content: @Composable () -> Unit) {
    val viewModel: NavigationBarViewModel = provideHiltViewModel()
    val pages = viewModel.pagesStore.asStateValue()
    if (pages.isNullOrEmpty()) {
        content()
        return
    }
    val visibilityStore = viewModel.visibilityStore
    val drawerState: DrawerState = rememberDrawerState(getDrawerValue(visibilityStore)) {
        visibilityStore.set(it == DrawerValue.Open)
        true
    }
    DrawerVisibilityHandler(visibilityStore, drawerState)
    DismissibleNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DismissibleDrawerSheet(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                val selectedPage = viewModel.selectedPageStore.asStateValue()
                pages.forEach { page ->
                    val selected = page.id == selectedPage?.id
                    NavigationDrawerItem(
                        label = { page.getLabel()?.let { Text(text = it) } },
                        icon = { AnyIcon(model = page.getIcon(selected)) },
                        selected = selected,
                        onClick = {
                            page.onClick()
                            visibilityStore.set(false)
                        }
                    )
                }
            }
        },
        content = content
    )
}

@Stable
private fun getDrawerValue(visibilityStore: StoreObject<Boolean>): DrawerValue {
    return if (visibilityStore.get() == true) DrawerValue.Open else DrawerValue.Closed
}

@Composable
private fun DrawerVisibilityHandler(
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