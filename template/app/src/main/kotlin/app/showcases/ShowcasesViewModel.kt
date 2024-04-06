package app.showcases

import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowcasesViewModel @Inject constructor(
    val navigationState: NavigationState,
) : BaseViewModel() {

    val hintStore = StoreObject(false)
    val showcasesStore = StoreObject(Showcases.all)

    fun onShowHint() {
        hintStore.set(true)
    }

}
