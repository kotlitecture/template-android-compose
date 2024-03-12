package core.ui.theme.material3

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import core.ui.theme.ThemeDataProvider

data class Material3ThemeDataProvider(
    override val dark: Boolean,
    val colorScheme: ColorScheme
) : ThemeDataProvider() {

    fun createTypography(fontFamily: FontFamily): Typography {
        val typography = Typography()
        if (typography.bodyLarge.fontFamily != fontFamily) {
            return Typography(
                displayLarge = typography.displayLarge.copy(fontFamily = fontFamily),
                displayMedium = typography.displayMedium.copy(fontFamily = fontFamily),
                displaySmall = typography.displaySmall.copy(fontFamily = fontFamily),
                headlineLarge = typography.headlineLarge.copy(fontFamily = fontFamily),
                headlineMedium = typography.headlineMedium.copy(fontFamily = fontFamily),
                headlineSmall = typography.headlineSmall.copy(fontFamily = fontFamily),
                titleLarge = typography.titleLarge.copy(fontFamily = fontFamily),
                titleMedium = typography.titleMedium.copy(fontFamily = fontFamily),
                titleSmall = typography.titleSmall.copy(fontFamily = fontFamily),
                bodyLarge = typography.bodyLarge.copy(fontFamily = fontFamily),
                bodyMedium = typography.bodyMedium.copy(fontFamily = fontFamily),
                bodySmall = typography.bodySmall.copy(fontFamily = fontFamily),
                labelLarge = typography.labelLarge.copy(fontFamily = fontFamily),
                labelMedium = typography.labelMedium.copy(fontFamily = fontFamily),
                labelSmall = typography.labelSmall.copy(fontFamily = fontFamily)
            )
        } else {
            return typography
        }
    }

    fun createColorScheme(ready: Boolean): ColorScheme {
        return if (!ready) {
            colorScheme.copy(background = Color.Unspecified)
        } else {
            colorScheme
        }
    }

}