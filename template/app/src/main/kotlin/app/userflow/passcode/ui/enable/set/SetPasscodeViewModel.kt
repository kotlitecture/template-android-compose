package app.userflow.passcode.ui.enable.set

import app.userflow.passcode.PasscodeState
import app.userflow.passcode.ui.enable.confirm.ConfirmPasscodeDestination
import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import core.ui.navigation.NavigationStrategy
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetPasscodeViewModel @Inject constructor(
    private val navigationState: NavigationState,
    passcodeState: PasscodeState,
) : BaseViewModel() {

    val passcodeLength = passcodeState.passcodeLength
    val enteredCodeStore = StoreObject<String>()

    override fun doBind() {
        enteredCodeStore.clear()
    }

    fun onBack() {
        navigationState.onBack()
    }

    fun onCodeChanged(enteredCode: String) {
        if (enteredCode.length > passcodeLength) {
            enteredCodeStore.clear()
            return
        }
        enteredCodeStore.set(enteredCode)
        if (enteredCode.length != passcodeLength) return
        val data = ConfirmPasscodeDestination.Data(enteredCode)
        navigationState.onNext(ConfirmPasscodeDestination, data, NavigationStrategy.ReplacePrevious)
    }

}
