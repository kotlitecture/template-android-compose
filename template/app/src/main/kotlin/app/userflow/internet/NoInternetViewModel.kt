package app.userflow.internet

import core.dataflow.datasource.network.NetworkSource
import core.ui.AppViewModel
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class NoInternetViewModel @Inject constructor(
    private val networkSource: NetworkSource
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