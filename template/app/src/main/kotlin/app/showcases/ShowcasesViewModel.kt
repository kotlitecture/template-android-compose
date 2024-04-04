package app.showcases

import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowcasesViewModel @Inject constructor(
    private val navigationState: NavigationState,
) : BaseViewModel() {

    val showcases = ShowcasesDestination.showcases

    fun onOpen(showcase: Showcase) {
        navigationState.onNext(showcase)
    }

}
