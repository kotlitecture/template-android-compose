package app.userflow.internet.no

import core.data.datasource.network.NetworkSource
import core.ui.BaseViewModel
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

/**
 * ViewModel class responsible for handling the state related to internet connectivity.
 *
 * @param networkSource The network data source providing information about internet connectivity.
 */
@HiltViewModel
class NoInternetViewModel @Inject constructor(
    private val networkSource: NetworkSource
) : BaseViewModel() {

    val isOnlineStore = StoreObject(true)

    override fun doBind() {
        launchAsync("doBind") {
            networkSource.isOnline()
                .filterNotNull()
                .collect(isOnlineStore::set)
        }
    }

}