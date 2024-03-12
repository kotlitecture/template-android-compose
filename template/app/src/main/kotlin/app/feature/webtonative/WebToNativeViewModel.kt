package app.feature.webtonative

import android.app.Application
import core.ui.AppViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebToNativeViewModel @Inject constructor(
    val app: Application,
    private val navigationState: NavigationState
) : AppViewModel() {

    fun onBack() {
        navigationState.onBack()
    }

}
