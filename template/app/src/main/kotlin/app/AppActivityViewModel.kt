package app

import app.userflow.template.TemplateDestination
import core.ui.AppViewModel
import core.ui.command.CommandState
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationState
import core.ui.state.StoreObject
import core.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppActivityViewModel @Inject constructor(
    val navigationState: NavigationState,
    val commandState: CommandState,
    val themeState: ThemeState,
    val appState: AppState,
) : AppViewModel() {

    val destinationStore = StoreObject<NavigationDestination<*>>()

    override fun doBind() {
        launchAsync("doBind") {
            destinationStore.set(TemplateDestination)
        }
    }

}