package core.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost

@Composable
fun NavigationHost(
    navigationContext: NavigationContext,
    navigationState: NavigationState,
    startDestination: NavigationDestination<*>
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navigationContext.navController,
        startDestination = startDestination.route,
        enterTransition = { fadeIn(animationSpec = tween(100)) },
        exitTransition = { fadeOut(animationSpec = tween(100)) },
        builder = { navigationState.destinations.forEach { it.bind(this) } }
    )
    NavigationProvider(navigationState, navigationContext)
}