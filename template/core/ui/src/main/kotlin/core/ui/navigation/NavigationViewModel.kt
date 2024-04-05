package core.ui.navigation

import core.ui.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

/**
 * ViewModel responsible for managing navigation-related functionality.
 */
class NavigationViewModel : BaseViewModel() {

    fun onBind(navigationState: NavigationState, context: NavigationContext) {
        navigationState.commandHandler = NavigationCommandHandler.create(context)
        launchAsync("currentDestinationStore") {
            context.navController.currentBackStackEntryFlow
                .mapNotNull { it.destination.route }
                .mapNotNull(NavigationDestination.Companion::getByRoute)
                .distinctUntilChanged()
                .collectLatest(navigationState.currentDestinationStore::set)
        }
    }

    fun onUnbind(navigationState: NavigationState) {
        navigationState.commandHandler = NavigationCommandHandler.create()
    }

}