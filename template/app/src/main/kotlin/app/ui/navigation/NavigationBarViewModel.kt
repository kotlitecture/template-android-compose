package app.ui.navigation

import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NavigationBarViewModel @Inject constructor(
    private val navigationBarState: NavigationBarState,
    private val navigationState: NavigationState,
) : BaseViewModel() {

    val restrictionStore = StoreObject(false)
    val pagesStore = navigationBarState.pagesStore
    val visibilityStore = navigationBarState.visibilityStore
    val selectedPageStore = navigationBarState.selectedPageStore

    override fun doBind() {
        launchAsync("doBind") {
            val destStore = navigationState.currentDestinationStore
            pagesStore.asFlow()
                .filterNotNull()
                .map { pages -> pages.associateBy { it.id } }
                .flatMapLatest { pages -> destStore.asFlow().map { pages to it } }
                .collectLatest { pair ->
                    val restricted = navigationBarState.restrictedDestinations
                    val allowed = navigationBarState.allowedDestinations
                    val destination = pair.second
                    if (allowed.isNotEmpty()) {
                        restrictionStore.set(!allowed.contains(destination))
                    } else if (restricted.isNotEmpty()) {
                        restrictionStore.set(restricted.contains(destination))
                    }
                    val page = destination?.id?.let(pair.first::get)
                    selectedPageStore.set(page)
                }
        }
    }

}