package app.userflow.template

import app.userflow.webtonative.WebToNativeDestination
import core.ui.AppViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val navigationState: NavigationState
) : AppViewModel() {

    fun onBack() {
        navigationState.onBack()
    }

    fun onTest() {
        navigationState.onNavigate(WebToNativeDestination, WebToNativeDestination.Data("https://google.com"))
    }

}
