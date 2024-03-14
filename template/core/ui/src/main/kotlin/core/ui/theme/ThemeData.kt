package core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

@Immutable
abstract class ThemeData {

    open val fontFamily: FontFamily = FontFamily.Default

    open val primary: Color = Color.White
    open val onPrimary: Color = Color.Black

    class NoThemeData : ThemeData()

    companion object {
        internal val localThemeData = staticCompositionLocalOf<ThemeData> { NoThemeData() }

        val current: ThemeData
            @Composable
            @ReadOnlyComposable
            get() = localThemeData.current
    }
}
