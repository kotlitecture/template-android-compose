package core.ui.theme.material3

import android.content.Context
import android.os.Build
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import core.ui.theme.ThemeDataProvider

/**
 * Object containing functions to generate Material3 theme data providers for different theme variations.
 */
object Material3Themes {

    /**
     * Generates a dark Material3 theme data provider.
     *
     * @return A theme data provider for the dark theme.
     */
    fun dark(): ThemeDataProvider<*> {
        return Material3ThemeDataProvider(
            id = "material_3_dark",
            dark = true,
            colorScheme = DarkColors
        )
    }

    /**
     * Generates a light Material3 theme data provider.
     *
     * @return A theme data provider for the light theme.
     */
    fun light(): ThemeDataProvider<*> {
        return Material3ThemeDataProvider(
            id = "material_3_light",
            dark = false,
            colorScheme = LightColors
        )
    }

    /**
     * Generates a dynamic dark Material3 theme data provider if supported by the device.
     *
     * @param context The context used to generate the theme data provider.
     * @return A theme data provider for the dynamic dark theme, or null if not supported.
     */
    fun dynamicDark(context: Context): ThemeDataProvider<*>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Material3ThemeDataProvider(
                id = "material_3_dynamic_dark",
                dark = true,
                colorScheme = dynamicDarkColorScheme(context)
            )
        } else {
            null
        }
    }

    /**
     * Generates a dynamic light Material3 theme data provider if supported by the device.
     *
     * @param context The context used to generate the theme data provider.
     * @return A theme data provider for the dynamic light theme, or null if not supported.
     */
    fun dynamicLight(context: Context): ThemeDataProvider<*>? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return Material3ThemeDataProvider(
                id = "material_3_dynamic_light",
                dark = false,
                colorScheme = dynamicLightColorScheme(context)
            )
        } else {
            null
        }
    }

}
