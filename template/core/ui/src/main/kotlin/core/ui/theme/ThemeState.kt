package core.ui.theme

import core.ui.state.StoreObject
import core.ui.state.StoreState

/**
 * Represents the state of the theme settings within the application.
 * This state includes the font family, available theme providers, and the current theme provider.
 *
 * @property persistentKey If the configuration needs to be automatically saved across app restarts,
 * this property represents the key of the value in the key-value storage.
 * A null value indicates that the configuration is not persistent.
 * @property availableThemes The list of all available theme data providers.
 * @property initialConfig the initial config to use.
 */
data class ThemeState(
    val persistentKey: String? = "theme_config",
    val availableThemes: List<ThemeDataProvider<*>>,
    val initialConfig: ThemeConfig,
) : StoreState() {

    /** Store object for the font family. */
    val configStore = StoreObject<ThemeConfig>()

    /** Store object for the theme dark mode state. */
    val systemDarkModeStore = StoreObject<Boolean>()

    fun findProviderById(id: String?): ThemeDataProvider<*>? {
        return availableThemes.find { it.id == id }
    }

}