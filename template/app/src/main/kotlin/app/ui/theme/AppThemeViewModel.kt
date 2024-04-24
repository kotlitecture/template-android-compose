package app.ui.theme

import core.ui.BaseViewModel
import core.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel class responsible for managing the theme state.
 */
@HiltViewModel
class AppThemeViewModel @Inject constructor(
    val themeState: ThemeState
) : BaseViewModel() {

    /**
     * Performs the binding operation.
     */
    override fun doBind() {
        themeState.configStore.set(themeState.defaultConfig)
    }

}