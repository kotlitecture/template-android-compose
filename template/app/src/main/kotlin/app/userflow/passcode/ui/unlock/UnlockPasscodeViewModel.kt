package app.userflow.passcode.ui.unlock

import android.app.Application
import app.AppState
import app.R
import app.userflow.passcode.PasscodeState
import app.userflow.passcode.repository.PasscodeRepository
import core.ui.BaseViewModel
import core.ui.misc.extensions.vibrateWrong
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationState
import core.ui.navigation.NavigationStrategy
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnlockPasscodeViewModel @Inject constructor(
    private val passcodeRepository: PasscodeRepository,
    private val navigationState: NavigationState,
    private val passcodeState: PasscodeState,
    private val appState: AppState,
    private val app: Application
) : BaseViewModel() {

    val passcodeLength = passcodeState.passcodeLength
    val canForgetPasscode = passcodeState.canForgetPasscode
    val biometricAvailable = StoreObject(false)
    val biometricEnabled = StoreObject(false)
    val enteredCodeStore = StoreObject<String>()
    val errorStore = StoreObject<String>()

    private val dataStore = StoreObject<UnlockPasscodeDestination.Data>()
    private val attemptsStore = StoreObject(0)

    fun onBack() {
        navigationState.onBack()
    }

    fun onBind(data: UnlockPasscodeDestination.Data?) {
        launchAsync("onBind") {
            if (passcodeRepository.isEnabled()) {
                dataStore.set(data)
            } else {
                unlock()
            }
        }
    }

    override fun doBind() {
        launchAsync("doBind") {
            if (passcodeRepository.isBiometricEnabled()) {
                biometricEnabled.set(true)
                biometricAvailable.set(passcodeRepository.isBiometricAvailable())
            }
        }
    }

    fun onCodeChanged(enteredCode: String) {
        if (enteredCode.length > passcodeLength) {
            enteredCodeStore.clear()
            return
        }
        enteredCodeStore.set(enteredCode)
        if (enteredCode.isNotEmpty()) {
            errorStore.clear()
        }
        if (enteredCode.length != passcodeLength) {
            return
        }
        launchAsync("onCodeChanged", appState) {
            when {
                passcodeRepository.getCode() != enteredCode -> registerWrongAttempt(enteredCode)
                else -> unlock()
            }
        }
    }

    fun onBiometricRequest() {
        biometricAvailable.set(true)
    }

    fun onBiometricUnlock() {
        unlock()
    }

    fun onForgot() {
        //
    }

    private fun unlock() {
        val data = dataStore.get()
        if (data == null) {
            reset()
            return
        }
        launchAsync("unlock", appState) {
            when {
                data.nextRoute != null -> {
                    val strategy = NavigationStrategy.ReplacePrevious
                    val destination = NavigationDestination.getByRoute(data.nextRoute)!!
                    navigationState.onNext(destination, strategy = strategy)
                }

                data.back -> {
                    onBack()
                }

                else -> {
                    try {
                        passcodeState.unlockHandler.onUnlock()
                    } finally {
                        enteredCodeStore.clear()
                    }
                }
            }
        }
    }

    private fun registerWrongAttempt(code: String) {
        val attempts = attemptsStore.getNotNull() + 1
        attemptsStore.set(attempts)
        enteredCodeStore.clear()
        app.vibrateWrong()
        val remaining = passcodeState.unlockAttemptsCount - attempts
        if (remaining <= 0) {
            reset()
        } else {
            errorStore.set(app.getString(R.string.passcode_unlock_error, remaining))
            launchAsync("registerWrongAttempt") {
                passcodeState.unlockHandler.onWrongAttempt(attempts, code)
            }
        }
    }

    private fun reset() {
        launchAsync("reset", appState) {
            passcodeRepository.reset()
            passcodeState.unlockHandler.onReset()
        }
    }

}
