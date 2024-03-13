package core.ui.theme.material3

import androidx.compose.ui.text.font.FontFamily
import core.ui.AppViewModel
import core.ui.state.StoreObject
import core.ui.theme.ThemeState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class Material3ThemeViewModel : AppViewModel() {

    val themeDataStore = StoreObject<Material3ThemeData>()

    fun onBind(themeState: ThemeState) {
        launchAsync("themeState", themeState) {
            themeState.themeStore.asFlow()
                .mapNotNull { it as? Material3ThemeDataProvider }
                .map { provider ->
                    val fontFamily = themeState.fontFamilyStore.getNotNull()
                    Material3ThemeData(
                        fontFamily = fontFamily,
                        provider = provider,
                        colorScheme = provider.createColorScheme(true),
                        typography = provider.createTypography(fontFamily)
                    )
                }
                .collectLatest(themeDataStore::set)
        }
        launchAsync("fontFamilyStore") {
            themeState.fontFamilyStore.asFlow()
                .filterNotNull()
                .collectLatest { updateTheme(it) }
        }
        if (themeState.themeStore.get() == null) {
            themeState.themeStore.set(themeState.themesStore.get()?.firstOrNull())
        }
    }

    private fun updateTheme(fontFamily: FontFamily) {
        val themeData = themeDataStore.get() ?: return
        if (themeData.fontFamily == fontFamily) return
        themeDataStore.set(Material3ThemeData(
            fontFamily = fontFamily,
            provider = themeData.provider,
            colorScheme = themeData.colorScheme,
            typography = themeData.provider.createTypography(fontFamily)
        ))
    }

}