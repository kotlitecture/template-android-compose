package core.ui.navigation

import android.net.Uri
import core.ui.BaseViewModel
import core.ui.state.DataState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull

/**
 * ViewModel responsible for managing navigation-related functionality.
 */
class NavigationViewModel : BaseViewModel() {

    fun onBind(navigationState: NavigationState, context: NavigationContext) {
        launchAsync("currentDestinationStore") {
            context.navController.currentBackStackEntryFlow
                .mapNotNull { it.destination.route }
                .mapNotNull(NavigationDestination.Companion::getByRoute)
                .distinctUntilChanged()
                .collectLatest(navigationState.currentDestinationStore::set)
        }
        launchMain("navigationStore") {
            navigationState.navigationStore.asFlow()
                .filterNotNull()
                .collect {
                    try {
                        val strategy = it.strategy
                        val destination = it.destination
                        val controller = context.navController
                        if (destination != null) {
                            destination.navigate(it.data, strategy, controller)
                        } else {
                            strategy.proceed(null, Uri.EMPTY, controller)
                        }
                    } catch (e: Exception) {
                        val dataState = DataState.Error("Navigation", e)
                        navigationState.dataStateStore.set(dataState)
                    }
                }
        }
    }

}