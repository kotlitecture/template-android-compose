package core.ui.app.loading

import core.data.state.StoreObject
import core.datasource.network.INetworkSource
import core.ui.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import org.tinylog.Logger
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val networkSource: INetworkSource
) : BaseViewModel() {

    private val loadingTimeout = 30_000L
    private val isOnline = StoreObject(true)
    private val isLoading = StoreObject(false)

    fun isLoading(): Boolean = isLoading.asStateValueNotNull()
    fun isOnline(): Boolean = isOnline.asStateValueNotNull()

    override fun doBind() {
        launchAsync("withLoadingState") {
            appState.loadingStore.asFlow()
                .filterNotNull()
                .distinctUntilChanged()
                .collectLatest { loading ->
                    delay(refreshingDelay)
                    if (loading) {
                        Logger.debug("loading :: state = {}", true)
                        isLoading.set(true)
                        onClearFocus()
                        delay(loadingTimeout)
                        Logger.debug("loading :: state timeout = {}", false)
                        isLoading.set(false)
                    } else {
                        Logger.debug("loading :: state = {}", false)
                        isLoading.set(false)
                    }
                }
        }
        launchAsync("withInternetListener") {
            networkSource.isOnline()
                .filterNotNull()
                .collect(isOnline::set)
        }
    }

}