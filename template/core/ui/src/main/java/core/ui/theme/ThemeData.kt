package core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily

@Immutable
abstract class ThemeData {

    open val fontFamily: FontFamily = FontFamily.Default

    class NoThemeData : ThemeData()

    companion object {
        internal val localThemeData = staticCompositionLocalOf<ThemeData> { NoThemeData() }

        @Composable
        @ReadOnlyComposable
        fun get(): ThemeData = localThemeData.current
    }
}
