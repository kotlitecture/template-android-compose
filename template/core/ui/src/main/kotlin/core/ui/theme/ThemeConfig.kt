package core.ui.theme

import androidx.compose.ui.text.font.FontFamily
import core.ui.theme.material3.Material3Dark
import core.ui.theme.material3.Material3Light

/**
 * Represents the configuration for theming in the application.
 *
 * @param defaultTheme The initial theme data provider to be used by default.
 * @param fontFamily The default font family to be used in the application.
 * @param lightTheme The default light theme to be used.
 * @param darkTheme The default dark theme to be used.
 * @param availableThemes The list of available theme data providers.
 * @param autoDark Whether to enable automatic dark mode based on system settings.
 */
data class ThemeConfig(
    val defaultTheme: ThemeDataProvider<*>,
    val fontFamily: FontFamily = FontFamily.Default,
    val lightTheme: ThemeDataProvider<*> = defaultTheme,
    val darkTheme: ThemeDataProvider<*> = defaultTheme,
    val availableThemes: List<ThemeDataProvider<*>>,
    val autoDark: Boolean = true
) {
    companion object {
        val Default = ThemeConfig(
            availableThemes = listOf(Material3Light, Material3Dark),
            defaultTheme = Material3Light,
            lightTheme = Material3Light,
            darkTheme = Material3Dark,
        )
    }
}