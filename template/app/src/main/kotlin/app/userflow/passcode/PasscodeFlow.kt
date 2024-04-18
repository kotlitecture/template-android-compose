package app.userflow.passcode

import app.userflow.passcode.repository.PasscodeRepository
import app.userflow.passcode.ui.unlock.UnlockPasscodeDestination
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationState
import core.ui.navigation.NavigationStrategy
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * A ViewModel-scoped class for handling passcode-related flows.
 */
@ViewModelScoped
class PasscodeFlow @Inject constructor(
    private val passcodeRepository: PasscodeRepository,
    private val navigationState: NavigationState,
) {

    /**
     * Navigates to the specified destination with optional data and strategy, considering passcode status.
     *
     * If the passcode is enabled, the user will first be navigated to the [UnlockPasscodeDestination] to confirm their credentials.
     *
     * Either way, they will then be navigated to the provided destination.
     *
     * @param destination The destination to navigate to.
     * @param data Optional data to pass to the destination.
     * @param strategy The navigation strategy to use.
     */
    suspend fun <D> navigateWithPasscode(
        destination: NavigationDestination<D>,
        data: D? = null,
        strategy: NavigationStrategy = destination.navStrategy
    ) {
        if (passcodeRepository.isEnabled()) {
            navigationState.onNext(
                UnlockPasscodeDestination,
                UnlockPasscodeDestination.Data(
                    nextDestinationUri = destination.toUriString(data),
                    canNavigateBack = true
                )
            )
        } else {
            navigationState.onNext(destination, data, strategy)
        }
    }

}