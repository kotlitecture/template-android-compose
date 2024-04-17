package app.userflow.passcode.ui.unlock

import android.app.Application
import app.AppNavigationRouter
import app.AppState
import app.R
import app.userflow.passcode.PasscodeState
import app.userflow.passcode.repository.PasscodeRepository
import app.userflow.passcode.ui.reset.ResetPasscodeDestination
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
    private val navigationRouter: AppNavigationRouter,
    private val navigationState: NavigationState,
    private val passcodeState: PasscodeState,
    private val appState: AppState,
    private val app: Application
) : BaseViewModel() {

    val canForgetPasscode = passcodeState.canForgetPasscode
    val passcodeLength = passcodeState.passcodeLength
    val biometricAvailable = StoreObject(false)
    val biometricEnabled = StoreObject(false)
    val enteredCodeStore = StoreObject<String>()
    val errorStore = StoreObject<String>()

    private val dataStore = StoreObject<UnlockPasscodeDestination.Data>()

    fun onBack() {
        navigationState.onBack()
    }

    fun onBind(data: UnlockPasscodeDestination.Data?) {
        enteredCodeStore.clear()
        dataStore.set(data)
        launchAsync("isBiometricEnabled") {
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
                passcodeRepository.canUnlock(enteredCode) -> unlock()
                passcodeRepository.isEnabled() -> unlockFailed()
                else -> unlockDefault()
            }
        }
    }

    fun onBiometricRequest() {
        biometricAvailable.set(true)
    }

    fun onBiometricUnlock() {
        launchAsync("onBiometricUnlock", appState) {
            unlock()
        }
    }

    fun onForgot() {
        navigationState.onNext(ResetPasscodeDestination)
    }

    private suspend fun unlock() {
        passcodeRepository.unlock()
        val data = dataStore.get()
        when {
            data?.nextRoute != null -> unlockRoute(data.nextRoute)
            data?.nextRouteIsPrevious == true -> onBack()
            else -> unlockDefault()
        }
    }

    private suspend fun unlockRoute(route: String) {
        val strategy = NavigationStrategy.ReplacePrevious
        val destination = NavigationDestination.getByRoute(route)!!
        navigationState.onNext(destination, strategy = strategy)
    }

    private suspend fun unlockFailed() {
        val remainingAttempts = passcodeRepository.getRemainingAttempts()
        errorStore.set(app.getString(R.string.passcode_unlock_error, remainingAttempts))
        enteredCodeStore.clear()
        app.vibrateWrong()
    }

    private suspend fun unlockDefault() {
        navigationState.onNext(
            destination = navigationRouter.getStartDestination(),
            strategy = NavigationStrategy.ClearHistory
        )
    }

}
