package app

import app.userflow.template.TemplateDestination
import core.ui.AppViewModel
import core.ui.navigation.NavigationDestination
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppActivityViewModel @Inject constructor(val appState: AppState) : AppViewModel() {

    val destinationStore = StoreObject<NavigationDestination<*>>()

    override fun doBind() {
        launchAsync("doBind") {
            destinationStore.set(TemplateDestination())
        }
    }

}