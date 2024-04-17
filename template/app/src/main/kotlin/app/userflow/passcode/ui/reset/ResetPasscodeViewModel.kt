package app.userflow.passcode.ui.reset

import app.AppNavigationRouter
import app.AppState
import app.userflow.passcode.repository.PasscodeRepository
import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import core.ui.navigation.NavigationStrategy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasscodeViewModel @Inject constructor(
    private val passcodeRepository: PasscodeRepository,
    private val navigationRouter: AppNavigationRouter,
    private val navigationState: NavigationState,
    private val appState: AppState
) : BaseViewModel() {

    fun onBack() {
        navigationState.onBack()
    }

    fun onReset() {
        launchAsync("onReset", appState) {
            passcodeRepository.reset()
            navigationState.onNext(
                destination = navigationRouter.getStartDestination(),
                strategy = NavigationStrategy.ClearHistory
            )
        }
    }

}
