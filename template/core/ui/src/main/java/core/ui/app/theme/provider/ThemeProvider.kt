package core.ui.app.theme.provider

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import core.ui.app.theme.AppAnimation
import core.ui.app.theme.AppBehaviour
import core.ui.app.theme.AppColorScheme
import core.ui.app.theme.AppSize
import core.ui.app.theme.AppTypography

@Immutable
abstract class ThemeProvider(private val fontFamily: FontFamily) {

    private val colorScheme by lazy { createMaterialColorScheme() }
    val materialTypography: Typography by lazy { createMaterialTypography() }
    val appColorScheme: AppColorScheme by lazy { createAppColorScheme() }
    val appTypography: AppTypography by lazy { createAppTypography() }
    val behaviour: AppBehaviour by lazy { createAppBehaviour() }
    val animation: AppAnimation by lazy { createAppAnimation() }
    val size: AppSize by lazy { createAppSize() }

    var materialColorScheme: ColorScheme = colorScheme

    @Composable
    fun OnBind(ready: Boolean) {
        materialColorScheme =
            if (!ready) colorScheme.copy(background = Color.Unspecified) else colorScheme
    }

    protected open fun createAppBehaviour(): AppBehaviour {
        return AppBehaviour()
    }

    protected open fun createAppAnimation(): AppAnimation {
        return AppAnimation()
    }

    protected open fun createAppSize(): AppSize {
        return AppSize()
    }

    protected open fun createAppTypography(): AppTypography {
        val typography = Typography()
        return AppTypography(
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
    }

    protected open fun createMaterialTypography(): Typography {
        return Typography(
            displayLarge = appTypography.displayLarge,
            displayMedium = appTypography.displayMedium,
            displaySmall = appTypography.displaySmall,
            headlineLarge = appTypography.headlineLarge,
            headlineMedium = appTypography.headlineMedium,
            headlineSmall = appTypography.headlineSmall,
            titleLarge = appTypography.titleLarge,
            titleMedium = appTypography.titleMedium,
            titleSmall = appTypography.titleSmall,
            bodyLarge = appTypography.bodyLarge,
            bodyMedium = appTypography.bodyMedium,
            bodySmall = appTypography.bodySmall,
            labelLarge = appTypography.labelLarge,
            labelMedium = appTypography.labelMedium,
            labelSmall = appTypography.labelSmall
        )
    }

    protected abstract fun createAppColorScheme(): AppColorScheme

    protected abstract fun createMaterialColorScheme(): ColorScheme

}