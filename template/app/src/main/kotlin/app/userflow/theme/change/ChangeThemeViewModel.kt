package app.userflow.theme.change

import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import core.ui.state.StoreObject
import core.ui.theme.ThemeConfig
import core.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * ViewModel for handling theme change functionality.
 *
 * @property navigationState The navigation state to manage navigation within the app.
 * @property themeState The theme state to manage theme-related operations.
 */
@HiltViewModel
class ChangeThemeViewModel @Inject constructor(
    private val navigationState: NavigationState,
    private val themeState: ThemeState
) : BaseViewModel() {

    val configStore = themeState.configStore
    val dynamicColorsStore = StoreObject<Boolean>()

    override fun doBind() {
        val dynamicConfig = themeState.dynamicConfig
        if (dynamicConfig != null) {
            launchAsync("dynamicColors") {
                val dynamicThemes = setOf(dynamicConfig.lightTheme.id, dynamicConfig.darkTheme.id)
                themeState.configStore.asFlow()
                    .filterNotNull()
                    .map { it.defaultTheme.id }
                    .map { it in dynamicThemes }
                    .distinctUntilChanged()
                    .collectLatest(dynamicColorsStore::set)
            }
        }
    }

    fun onBack() {
        navigationState.onBack()
    }

    fun onEnableDynamicColors() {
        val dynamicConfig = themeState.dynamicConfig ?: return
        val currentConfig = themeState.configStore.get() ?: return
        themeState.configStore.set(copyState(dynamicConfig, currentConfig))
    }

    fun onDisableDynamicColors() {
        val defaultConfig = themeState.defaultConfig
        val currentConfig = themeState.configStore.get() ?: return
        themeState.configStore.set(copyState(defaultConfig, currentConfig))
    }

    fun onUseSystemDefault() {
        themeState.setAuto()
    }

    fun onUseLight() {
        themeState.setLight()
    }

    fun onUseDark() {
        themeState.setDark()
    }

    private fun copyState(to: ThemeConfig, from: ThemeConfig): ThemeConfig {
        return to.copy(
            defaultTheme = if (from.defaultTheme.dark) to.darkTheme else to.lightTheme,
            autoDark = from.autoDark
        )
    }

}
