package app

import app.feature.template.TemplateDestination
import core.data.state.StoreObject
import core.ui.navigation.NavigationDestination
import core.ui.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppActivityViewModel @Inject constructor() : AppViewModel() {

    val destinationStore = StoreObject<NavigationDestination<*>>()

    override fun doBind() {
        launchAsync("doBind") {
            destinationStore.set(TemplateDestination())
        }
    }

}