package app.userflow.loading

import core.data.state.StoreObject
import core.ui.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import org.tinylog.Logger
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor() : AppViewModel() {

    private val loadingTimeout = 30_000L

    val isLoadingStore = StoreObject(false)

    override fun doBind() {
        launchAsync("doBind") {
            appState.loadingStore.asFlow()
                .filterNotNull()
                .distinctUntilChanged()
                .collectLatest { loading ->
                    delay(refreshingDelay)
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