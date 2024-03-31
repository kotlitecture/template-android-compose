package app.ui.navigation.left

import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import app.provideHiltViewModel
import app.ui.component.AnyIcon
import app.ui.navigation.DrawerVisibilityHandler
import app.ui.navigation.NavigationBarViewModel
import app.ui.navigation.getDrawerValue

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
            DismissibleDrawerSheet {
                val selected = viewModel.selectedPageStore.asStateValue()
                pages.forEach { page ->
                    NavigationDrawerItem(
                        label = { page.getLabel()?.let { Text(text = it) } },
                        icon = { AnyIcon(model = page.getIcon()) },
                        selected = page.id == selected?.id,
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