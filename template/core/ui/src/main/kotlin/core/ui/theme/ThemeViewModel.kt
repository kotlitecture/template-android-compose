@file:OptIn(ExperimentalCoroutinesApi::class)

package core.ui.theme

import core.ui.AppViewModel
import core.ui.state.StoreObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

/**
 * ViewModel responsible for managing the app theme state.
 */
class ThemeViewModel : AppViewModel() {

    /** Store object for the theme data. */
    val dataStore = StoreObject<ThemeData>()

    /**
     * Binds the theme state to the ViewModel.
     *
     * @param state The state of the theme to be bound.
     */
    fun onBind(state: ThemeState) {
        launchAsync("onBind") {
            state.systemDarkModeStore.asFlow()
                .filterNotNull()
                .flatMapLatest { darkMode ->
                    state.configStore.asFlow()
                        .filterNotNull()
                        .mapNotNull { Data(it, darkMode) }
                }
                .map { data ->
                    val autoDark = data.config.autoDark
                    val provider = when {
                        autoDark && data.darkMode -> data.config.darkTheme
                        autoDark && !data.darkMode -> data.config.lightTheme
                        else -> data.config.defaultTheme
                    }
                    provider.provide(data.config)
                }
                .collect(dataStore::set)
        }
        launchAsync("config") {
            val initialConfig = state.getConfig()
            state.configStore.set(initialConfig)
            state.configStore.asFlow()
                .filterNotNull()
                .filter { it !== initialConfig }
                .collectLatest(state.setConfig::invoke)
        }
    }

    private data class Data(
        val config: ThemeConfig,
        val darkMode: Boolean
    )

}