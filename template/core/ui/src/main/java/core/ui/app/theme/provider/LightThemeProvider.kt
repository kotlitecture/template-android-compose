package core.ui.app.theme.provider

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.text.font.FontFamily
import core.ui.app.theme.AppColorScheme
import core.ui.app.theme.provider.impl.LightColors

internal class LightThemeProvider(fontFamily: FontFamily) : ThemeProvider(fontFamily) {

    override fun createMaterialColorScheme(): ColorScheme {
        return LightColors
    }

    override fun createAppColorScheme(): AppColorScheme {
        return AppColorScheme(material = materialColorScheme, isDark = false)
    }

}