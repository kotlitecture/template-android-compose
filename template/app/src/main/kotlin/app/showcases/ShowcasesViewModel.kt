package app.showcases

import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowcasesViewModel @Inject constructor(
    val navigationState: NavigationState,
) : BaseViewModel() {

    val showcases = ShowcasesDestination.showcases

}