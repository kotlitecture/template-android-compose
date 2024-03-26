package core.ui.theme

import core.ui.AppViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

/**
 * ViewModel responsible for managing the app theme state.
 */
class ThemeViewModel : AppViewModel() {

    /**
     * Binds the theme state to the ViewModel.
     *
     * @param state The state of the Material3 theme to be bound.
     */
    fun onBind(state: ThemeState) {
        launchAsync("themeProviderStore", state) {
            state.providerStore.asFlow()
                .filterNotNull()
                .map { provider -> provider.provide(state.configStore.getNotNull()) }
                .collectLatest(state.dataStore::set)
        }
        launchAsync("configStore") {
            state.configStore.asFlow()
                .filterNotNull()
                .map { config -> state.providerStore.getNotNull().provide(config) }
                .collectLatest(state.dataStore::set)
        }
        launchAsync("systemDarkModeStore") {
            state.systemDarkModeStore.asFlow()
                .filterNotNull()
                .filter { state.configStore.getNotNull().autoDark }
                .map { dark -> if (dark) state.darkTheme else state.lightTheme }
                .collectLatest(state.providerStore::set)
        }
    }

}