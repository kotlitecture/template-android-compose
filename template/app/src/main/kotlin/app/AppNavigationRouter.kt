package app

import app.showcases.ShowcasesDestination
import app.userflow.passcode.repository.PasscodeRepository
import app.userflow.passcode.ui.unlock.UnlockPasscodeDestination
import core.ui.navigation.NavigationDestination
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * ViewModel-scoped class representing the application navigation router.
 */
@ViewModelScoped
class AppNavigationRouter @Inject constructor(
    private val passcodeRepository: PasscodeRepository
) {

    /**
     * Returns the start destination based on the current passcode status.
     *
     * @return The start destination.
     */
    suspend fun getStartDestination(): NavigationDestination<*> {
        return when {
            passcodeRepository.isLocked() -> UnlockPasscodeDestination
            else -> ShowcasesDestination
        }
    }

}