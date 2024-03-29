package app

import app.userflow.template.TemplateDestination
import core.ui.AppViewModel
import core.ui.navigation.NavigationState
import core.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppActivityViewModel @Inject constructor(
    val navigationState: NavigationState,
    val themeState: ThemeState,
    val appState: AppState,
) : AppViewModel() {

    override fun doBind() {
        launchAsync("doBind") {
            navigationState.startDestinationStore.set(TemplateDestination)
        }
    }

}