package app.userflow.loader

import core.ui.AppViewModel
import core.ui.state.DataState
import core.ui.state.StoreObject
import core.ui.state.StoreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.tinylog.Logger
import javax.inject.Inject

@HiltViewModel
class DataLoaderViewModel @Inject constructor() : AppViewModel() {

    private val loadingTimeout = 30_000L
    private val loadingDelay = 50L

    val isLoadingStore = StoreObject(false)

    fun onBind(state: StoreState) {
        launchAsync("doBind") {
            state.dataStateStore.asFlow()
                .filterNotNull()
                .map { it is DataState.Loading }
                .distinctUntilChanged()
                .collectLatest { loading ->
                    delay(loadingDelay)
                    if (loading) {
                        Logger.debug("loading :: state = {}", true)
                        isLoadingStore.set(true)
                        delay(loadingTimeout)
                        Logger.debug("loading :: state timeout = {}", false)
                        isLoadingStore.set(false)
                    } else {
                        Logger.debug("loading :: state = {}", false)
                        isLoadingStore.set(false)
                    }
                }
        }
    }

}