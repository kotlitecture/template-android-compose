package core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

/**
 * Abstract class representing theme data.
 *
 * @property fontFamily The font family used in the theme.
 * @property primary The primary color in the theme.
 * @property onPrimary The color used for texts and icons on the primary background.
 */
abstract class ThemeData {

    open val fontFamily: FontFamily = FontFamily.Default

    open val primary: Color = Color.White
    open val onPrimary: Color = Color.Black

    /** Represents no theme data. */
    class NoThemeData : ThemeData()

    companion object {
        /** Local composition used to access the current theme data. */
        internal val localThemeData = staticCompositionLocalOf<ThemeData> { NoThemeData() }

        /** Returns the current theme data in the composition. */
        val current: ThemeData
            @Composable
            @ReadOnlyComposable
            get() = localThemeData.current
    }
}
