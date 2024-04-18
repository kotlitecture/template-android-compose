package app.userflow.passcode.ui.enable.confirm

import android.app.Application
import app.AppState
import app.R
import app.userflow.passcode.repository.PasscodeRepository
import app.userflow.passcode.ui.enable.biometric.SetBiometricDestination
import core.ui.BaseViewModel
import core.ui.misc.extensions.vibrateWrong
import core.ui.navigation.NavigationState
import core.ui.navigation.NavigationStrategy
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmPasscodeViewModel @Inject constructor(
    private val passcodeRepository: PasscodeRepository,
    private val navigationState: NavigationState,
    private val appState: AppState,
    private val app: Application
) : BaseViewModel() {

    val enteredCodeStore = StoreObject<String>()
    val errorStore = StoreObject<String>()

    override fun doBind() {
        enteredCodeStore.clear()
    }

    fun onBack() {
        navigationState.onBack()
    }

    fun onCodeChanged(expectedCode: String, enteredCode: String) {
        if (enteredCode.length > expectedCode.length) {
            enteredCodeStore.clear()
            return
        }
        enteredCodeStore.set(enteredCode)
        if (enteredCode.isNotEmpty()) {
            errorStore.clear()
        }
        if (enteredCode.length != expectedCode.length) {
            return
        }
        if (enteredCode != expectedCode) {
            errorStore.set(app.getString(R.string.passcode_enable_confirm_error))
            enteredCodeStore.clear()
            app.vibrateWrong()
            return
        }
        launchAsync("onCodeChanged", appState) {
            passcodeRepository.enablePasscode(expectedCode)
            if (passcodeRepository.isBiometricAvailable()) {
                val strategy = NavigationStrategy.ReplacePrevious
                navigationState.onNext(SetBiometricDestination, strategy = strategy)
            } else {
                navigationState.onBack()
            }
        }
    }

}
