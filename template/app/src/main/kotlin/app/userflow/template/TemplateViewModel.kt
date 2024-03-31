package app.userflow.template

import app.AppState
import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the template screen.
 *
 * @param navigationState The navigation state for managing app navigation.
 * @param appState The app state for managing app-wide state.
 */
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val navigationState: NavigationState,
    private val appState: AppState
) : BaseViewModel() {

    fun onBack() {
        navigationState.onBack()
    }

}
