package core.ui.theme

import androidx.compose.ui.text.font.FontFamily
import core.ui.state.StoreObject
import core.ui.state.StoreState
import core.ui.theme.material3.Material3Dark
import core.ui.theme.material3.Material3Light

data class ThemeState(
    private val fontFamily: FontFamily = FontFamily.Default,
    private val themes: List<ThemeDataProvider>,
    private val theme: ThemeDataProvider
) : StoreState() {

    val fontFamilyStore = StoreObject(fontFamily)
    val themeProvidersStore = StoreObject(themes)
    val themeProviderStore = StoreObject(theme)
    val dataStore = StoreObject<ThemeData>()

    companion object {
        val Default by lazy {
            ThemeState(
                themes = listOf(Material3Light, Material3Dark),
                theme = Material3Light
            )
        }
    }

}