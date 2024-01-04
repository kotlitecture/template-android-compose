package core.ui.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import core.ui.app.theme.provider.DarkThemeProvider
import core.ui.app.theme.provider.LightThemeProvider
import core.ui.app.theme.provider.ThemeProvider

private val darkThemeProvider = DarkThemeProvider(AppFontFamily.default)
private val lightThemeProvider = LightThemeProvider(AppFontFamily.default)

private val localThemeProvider = staticCompositionLocalOf<ThemeProvider> { lightThemeProvider }

@Composable
fun AppTheme(
    isReady: State<Boolean> = mutableStateOf(true),
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            Color.Transparent,
            darkIcons = !isDark,
            isNavigationBarContrastEnforced = false
        )
    }

    val themeProvider: ThemeProvider = when {
        isDark -> darkThemeProvider
        else -> lightThemeProvider
    }

    themeProvider.OnBind(isReady.value)

    CompositionLocalProvider(
        localThemeProvider provides themeProvider
    ) {
        MaterialTheme(
            colorScheme = themeProvider.materialColorScheme,
            typography = themeProvider.materialTypography,
            content = content
        )
    }
}

object AppTheme {
    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = localThemeProvider.current.appTypography

    val materialColor: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = localThemeProvider.current.materialColorScheme

    val color: AppColorScheme
        @Composable
        @ReadOnlyComposable
        get() = localThemeProvider.current.appColorScheme

    val behaviour: AppBehaviour
        @Composable
        @ReadOnlyComposable
        get() = localThemeProvider.current.behaviour

    val anim: AppAnimation
        @Composable
        @ReadOnlyComposable
        get() = localThemeProvider.current.animation

    val size: AppSize
        @Composable
        @ReadOnlyComposable
        get() = localThemeProvider.current.size
}