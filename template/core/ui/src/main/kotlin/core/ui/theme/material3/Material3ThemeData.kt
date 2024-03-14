package core.ui.theme.material3

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import core.ui.theme.ThemeData

data class Material3ThemeData(
    override val fontFamily: FontFamily,
    val provider: Material3ThemeDataProvider,
    val colorScheme: ColorScheme,
    val typography: Typography,
) : ThemeData() {

    override val primary: Color = colorScheme.primary
    override val onPrimary: Color = colorScheme.onPrimary

    companion object {
        val current: Material3ThemeData
            @Composable
            @ReadOnlyComposable
            get() = ThemeData.current as Material3ThemeData
    }

}