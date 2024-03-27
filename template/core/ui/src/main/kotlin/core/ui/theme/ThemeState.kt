package core.ui.theme

import core.ui.state.StoreObject
import core.ui.state.StoreState

/**
 * Represents the state of the theme settings within the application.
 * This state includes the font family, available theme providers, and the current theme provider.
 *
 * @property getConfig The config to use.
 */
data class ThemeState(val getConfig: suspend () -> ThemeConfig) : StoreState() {

    /** Store object for the font family. */
    val configStore = StoreObject<ThemeConfig>()

    /** Store object for the theme dark mode state. */
    val systemDarkModeStore = StoreObject<Boolean>()

    companion object {
        /** Default theme state with Material3Light theme as default. */
        val Default by lazy { ThemeState(getConfig = { ThemeConfig.Default }) }
    }

}