package core.ui.theme

import core.ui.state.StoreObject
import core.ui.state.StoreState
import core.ui.theme.material3.Material3Dark
import core.ui.theme.material3.Material3Light

/**
 * Represents the state of the theme settings within the application.
 * This state includes the font family, available theme providers, and the current theme provider.
 *
 * @property config The initial config to use.
 * @property themes The list of available theme data providers.
 * @property theme The initial theme data provider.
 */
data class ThemeState(
    val config: ThemeConfig = ThemeConfig(),
    val themes: List<ThemeDataProvider<*>>,
    val theme: ThemeDataProvider<*>,
    val lightTheme: ThemeDataProvider<*> = theme,
    val darkTheme: ThemeDataProvider<*> = theme,
) : StoreState() {

    /** Store object for the font family. */
    val configStore = StoreObject(config)

    /** Store object for the current theme data provider. */
    val providerStore = StoreObject(theme)

    /** Store object for the theme data. */
    val dataStore = StoreObject<ThemeData>()

    /** Store object for the theme dark mode state. */
    val systemDarkModeStore = StoreObject<Boolean>()

    companion object {
        /** Default theme state with Material3Light theme as default. */
        val Default by lazy {
            ThemeState(
                themes = listOf(Material3Light, Material3Dark),
                lightTheme = Material3Light,
                darkTheme = Material3Dark,
                theme = Material3Light
            )
        }
    }

}