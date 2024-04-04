package core.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost

@Composable
fun NavigationHost(
    navigationState: NavigationState,
    navigationContext: NavigationContext,
    startDestination: NavigationDestination<*>
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        startDestination = startDestination.route,
        navController = navigationContext.navController,
        builder = { navigationState.destinations.forEach { it.bind(this) } }
    )
    NavigationProvider(navigationState, navigationContext)
}