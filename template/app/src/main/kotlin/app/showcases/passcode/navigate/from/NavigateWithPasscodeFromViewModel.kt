package app.showcases.passcode.navigate.from

import app.showcases.passcode.navigate.to.NavigateWithPasscodeToDestination
import app.userflow.passcode.PasscodeFlow
import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigateWithPasscodeFromViewModel @Inject constructor(
    private val navigationState: NavigationState,
    private val passcodeFlow: PasscodeFlow
) : BaseViewModel() {

    fun onBack() {
        navigationState.onBack()
    }

    fun onNavigateToTarget() {
        launchAsync("onNavigateToTarget") {
            passcodeFlow.navigateWithPasscode(NavigateWithPasscodeToDestination)
        }
    }

}
