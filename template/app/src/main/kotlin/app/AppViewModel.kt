package app

import app.feature.template.TemplateDestination
import core.data.state.StoreObject
import core.ui.app.navigation.Destination
import core.ui.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : BaseViewModel() {

    val destinationStore = StoreObject<Destination<*>>()

    override fun doBind() {
        launchAsync("doBind") {
            destinationStore.set(TemplateDestination())
        }
    }

}