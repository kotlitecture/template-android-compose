package app

import app.userflow.template.TemplateDestination
import core.ui.AppViewModel
import core.ui.navigation.NavigationState
import core.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the main activity of the app.
 *
 * @param navigationState The navigation state for managing app navigation.
 * @param themeState The theme state for managing app themes.
 * @param appState The app state for managing app-wide state.
 */
@HiltViewModel
class AppActivityViewModel @Inject constructor(
    val navigationState: NavigationState,
    val themeState: ThemeState,
    val appState: AppState,
) : AppViewModel() {

    override fun doBind() {
        launchAsync("doBind") {
            // You can perform some logic before setting the initial destination.
            navigationState.startDestinationStore.set(TemplateDestination)
        }
    }

}