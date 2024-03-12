package app.userflow.internet

import core.data.state.StoreObject
import core.datasource.network.INetworkSource
import core.ui.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class NoInternetViewModel @Inject constructor(
    private val networkSource: INetworkSource
) : AppViewModel() {

    val isOnlineStore = StoreObject(true)

    override fun doBind() {
        launchAsync("doBind") {
            networkSource.isOnline()
                .filterNotNull()
                .collect(isOnlineStore::set)
        }
    }

}