package core.ui.theme

import androidx.compose.ui.text.font.FontFamily

/**
 * Represents the configuration for theming in the application.
 *
 * @param fontFamily The default font family to be used in the application.
 * @param autoDark Whether to enable automatic dark mode based on system settings.
 */
data class ThemeConfig(
    val fontFamily: FontFamily = FontFamily.Default,
    val autoDark: Boolean = true
)