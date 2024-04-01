package core.ui.theme

import core.ui.state.StoreObject
import core.ui.state.StoreState

/**
 * Represents the state of the theme settings within the application.
 * This state includes the font family, available theme providers, and the current theme provider.
 *
 * @property config the initial config to use.
 */
data class ThemeState(
    val config: ThemeConfig
) : StoreState() {

    /** Store object for the font family. */
    val configStore = StoreObject<ThemeConfig>()

    /** Store object for the theme dark mode state. */
    val systemDarkModeStore = StoreObject<Boolean>()

}