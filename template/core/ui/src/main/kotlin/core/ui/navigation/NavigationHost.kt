package core.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import core.ui.AppContext

@Composable
fun NavigationHost(
    appContext: AppContext,
    navigationState: NavigationState,
    startDestination: NavigationDestination<*>,
    destinations: List<NavigationDestination<*>>
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = appContext.navController,
        startDestination = startDestination.route,
        builder = { destinations.forEach { it.bind(this) } },
        enterTransition = { fadeIn(animationSpec = tween(100)) },
        exitTransition = { fadeOut(animationSpec = tween(100)) }
    )
    NavigationProvider(navigationState, appContext)
}