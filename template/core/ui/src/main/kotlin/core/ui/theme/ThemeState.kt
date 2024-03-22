package core.ui.theme

import androidx.compose.ui.text.font.FontFamily
import core.ui.state.StoreObject
import core.ui.state.StoreState
import core.ui.theme.material3.Material3Dark
import core.ui.theme.material3.Material3Light

/**
 * Represents the state of the theme settings within the application.
 * This state includes the font family, available theme providers, and the current theme provider.
 *
 * @property fontFamily The font family used in the application.
 * @property themes The list of available theme data providers.
 * @property theme The current theme data provider.
 */
data class ThemeState(
    private val fontFamily: FontFamily = FontFamily.Default,
    private val themes: List<ThemeDataProvider>,
    private val theme: ThemeDataProvider
) : StoreState() {

    /** Store object for the font family. */
    val fontFamilyStore = StoreObject(fontFamily)

    /** Store object for the list of available theme data providers. */
    val themeProvidersStore = StoreObject(themes)

    /** Store object for the current theme data provider. */
    val themeProviderStore = StoreObject(theme)

    /** Store object for the theme data. */
    val dataStore = StoreObject<ThemeData>()

    companion object {
        /** Default theme state with Material3Light theme as default. */
        val Default by lazy {
            ThemeState(
                themes = listOf(Material3Light, Material3Dark),
                theme = Material3Light
            )
        }
    }

}