package app.ui.navigation

import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
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
    navigationBarState: NavigationBarState,
    private val navigationState: NavigationState,
) : BaseViewModel() {

    val availablePagesStore = navigationBarState.pagesStore
    val activePageStore = navigationBarState.selectedPageStore

    override fun doBind() {
        launchAsync("doBind") {
            val destStore = navigationState.currentDestinationStore
            availablePagesStore.asFlow()
                .filterNotNull()
                .flatMapLatest { pages -> destStore.asFlow().map { pages to it } }
                .map { pair -> pair.first.find { it.id == pair.second?.id } }
                .collectLatest(activePageStore::set)
        }
    }

}