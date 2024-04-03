package app.userflow.theme.toggle

import core.ui.BaseViewModel
import core.ui.state.StoreObject
import core.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

/**
 * ViewModel responsible for toggling between light and dark themes.
 *
 * @param themeState The state of the theme to be toggled.
 */
@HiltViewModel
class ToggleThemeViewModel @Inject constructor(
    private val themeState: ThemeState,
) : BaseViewModel() {

    val dataStore: StoreObject<ToggleThemeData> = StoreObject()

    override fun doBind() {
        launchAsync("doBind") {
            themeState.dataStore.asFlow()
                .mapNotNull { it?.providerId }
                .mapNotNull(themeState::findProviderById)
                .distinctUntilChanged()
                .map { ToggleThemeData(it) }
                .collectLatest(dataStore::set)
        }
    }

    /**
     * Toggles the theme between light and dark modes.
     */
    fun onToggle() {
        val data = dataStore.get() ?: return
        if (data.isDark()) {
            themeState.setLight()
        } else {
            themeState.setDark()
        }
    }

}
