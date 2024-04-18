package app.userflow.passcode.ui.enable.biometric

import app.AppState
import app.userflow.passcode.repository.PasscodeRepository
import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetBiometricViewModel @Inject constructor(
    private val navigationState: NavigationState,
    private val repository: PasscodeRepository,
    private val appState: AppState
) : BaseViewModel() {

    fun onBack() {
        navigationState.onBack()
    }

    fun onEnable() {
        launchAsync("onEnable", appState) {
            repository.enableBiometric(true)
            navigationState.onBack()
        }
    }

    fun onSkip() {
        launchAsync("onSkip", appState) {
            repository.enableBiometric(false)
            navigationState.onBack()
        }
    }

}
