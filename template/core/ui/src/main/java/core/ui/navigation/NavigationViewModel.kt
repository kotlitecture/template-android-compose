package core.ui.navigation

import android.net.Uri
import core.data.state.DataState
import core.ui.AppContext
import core.ui.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : AppViewModel() {

    fun onBind(navigationState: NavigationState, context: AppContext) {
        launchAsync("destinationStore") {
            context.navController.currentBackStackEntryFlow
                .mapNotNull { it.destination.route }
                .mapNotNull(NavigationDestination.Companion::get)
                .distinctUntilChanged()
                .collectLatest(navigationState.destinationStore::set)
        }
        launchAsync("navigationStore") {
            navigationState.navigationStore.asFlow()
                .filterNotNull()
                .collectLatest {
                    try {
                        val data = it.data
                        val destination = it.destination
                        val controller = context.navController
                        val strategy = destination?.strategy ?: NavigationStrategy.Back
                        val uri = destination?.toUri(data) ?: Uri.EMPTY
                        val route = destination?.route
                        strategy.proceed(route, uri, controller)
                    } catch (e: Exception) {
                        val dataState = DataState.Error(it.uid.toString(), e)
                        navigationState.dataStateStore.set(dataState)
                    }
                }
        }
    }

}