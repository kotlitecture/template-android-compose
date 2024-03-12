package core.ui.theme

import androidx.compose.ui.text.font.FontFamily
import core.data.state.StoreObject
import core.data.state.StoreState

class ThemeState : StoreState() {

    val fontFamilyStore = StoreObject(FontFamily.Default)
    val themeStore = StoreObject<ThemeDataProvider>()

    val themesStore = StoreObject<List<ThemeDataProvider>>(listOf(
        Material3Light,
        Material3Dark
    ))

    companion object {
        val Default by lazy { ThemeState() }
    }

}