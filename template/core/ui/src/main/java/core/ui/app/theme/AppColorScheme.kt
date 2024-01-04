package core.ui.app.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

@Immutable
data class AppColorScheme(
    val material: ColorScheme,
    val isDark: Boolean
) {

    val positive = Color(0xFF3FCE89)
    val negative = Color(0xFFF2645C)
    val warning = Color(0xFFF2AD5C)
    val information = Color(0xFFfff8c3)
    val textLink = Color(0xFF3478F6)
    val white = Color.White
    val black = Color.Black

    var textPrimaryInverse = white
    val surfacePrimary = material.surface
    val surfaceBottomSheet = material.surface
    val surfaceSecondary = material.surfaceVariant
    val statusPrimary = surfacePrimary.blur()
    val iconPrimary = material.onSurface
    val dialogPrimary = material.surfaceVariant
    val dividerPrimary = material.onSurface.copy(0.05f)
    val dividerSecondary = material.onSecondaryContainer.copy(0.05f)
    val placeholderPrimary = material.onSurface.copy(0.1f)
    val placeholderSecondary = material.onSurface.copy(0.05f)
    val progressPrimary = material.tertiaryContainer
    val shadowPrimary = material.onSurface.copy(alpha = 0.06f)
    val textPrimary = material.onSurface
    val textSecondary = textPrimary.copy(0.45f)
    val textHint = material.onSurface.copy(0.25f)
    val textInformation = Color.Black.copy(alpha = 0.7f)
    val highlightTextPrimary = material.onSecondaryContainer.copy(alpha = 0.05f)
    val highlightTextSecondary = positive.copy(alpha = 0.55f)
    val buttonText = textPrimary
    val buttonTextInactive = textSecondary
    val buttonPrimary = material.tertiaryContainer
    val buttonPrimaryInactive = buttonPrimary.copy(0.45f)
    val buttonSecondary = material.surfaceVariant
    val buttonSecondaryInactive = buttonSecondary.copy(0.45f)
    val itemSelected = positive.copy(alpha = 0.15f)

    @Stable
    fun getTextVariant(primary: Boolean) = if (primary) textPrimary else textSecondary
    @Stable
    fun positive(yes: Boolean) = if (yes) positive else negative

    @Stable
    fun getVariant(
        warning: Boolean = false,
        negative: Boolean = false,
        positive: Boolean = false
    ): Color {
        return when {
            warning -> this.warning
            negative -> this.negative
            positive -> this.positive
            else -> this.textPrimary
        }
    }

    companion object {
        val card1 = Color(0xFF7C4DFF)
        val card2 = Color(0xFF40C4FF)
        val card3 = Color(0xFFAB47BC)
        val card4 = Color(0xFFFF9800)
        val card5 = Color(0xFF69F0AE)
        val positive = Color(0xFF3FCE89)
        val negative = Color(0xFFF2645C)
        val warning = Color(0xFFF2AD5C)
        val information = Color(0xFFfff8c3)
        val textLink = Color(0xFF3478F6)
        val white = Color.White
        val black = Color.Black
    }
}

@Stable
fun Color.onColor(background: Color): Color = compositeOver(background)
@Stable
fun Color.disabled(): Color = copy(alpha = 0.45f)
@Stable
fun Color.blur(): Color = copy(alpha = 0.92f)
