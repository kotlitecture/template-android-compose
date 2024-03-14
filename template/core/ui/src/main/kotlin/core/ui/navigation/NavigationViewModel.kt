package core.ui.navigation

import android.net.Uri
import core.ui.AppContext
import core.ui.AppViewModel
import core.ui.state.DataState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull

class NavigationViewModel : AppViewModel() {

    fun onBind(navigationState: NavigationState, context: AppContext) {
        launchAsync("destinationStore") {
            context.navController.currentBackStackEntryFlow
                .mapNotNull { it.destination.route }
                .mapNotNull(NavigationDestination.Companion::find)
                .distinctUntilChanged()
                .collectLatest(navigationState.destinationStore::set)
        }
        launchMain("navigationStore") {
            navigationState.navigationStore.asFlow()
                .filterNotNull()
                .collectLatest {
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
                        e.printStackTrace()
                        val dataState = DataState.Error(it.uid.toString(), e)
                        navigationState.dataStateStore.set(dataState)
                    }
                }
        }
    }

}