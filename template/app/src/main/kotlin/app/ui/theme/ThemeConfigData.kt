package app.ui.theme

import kotlinx.serialization.Serializable

/**
 * Represents the configuration data for themes.
 */
@Serializable
data class ThemeConfigData(
    val defaultThemeId: String? = null,
    val lightThemeId: String? = null,
    val darkThemeId: String? = null,
    val autoDark: Boolean? = null,
)