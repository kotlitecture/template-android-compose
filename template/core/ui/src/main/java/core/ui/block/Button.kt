package core.ui.block

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme
import core.ui.app.theme.disabled
import core.ui.app.theme.onColor
import core.ui.layout.item.lazyItem
import core.ui.misc.extension.withDashedBorder

@Immutable
data class ButtonAppearance(
    val modifier: Modifier,
    val textColorActive: Color,
    val textColorInactive: Color,
    val textStyle: TextStyle,
    val backgroundColorActive: Color,
    val backgroundColorInactive: Color,
    val cornerRadius: Dp,
    val borderWidth: Dp,
    val borderColorActive: Color,
    val borderColorInactive: Color,
    val height: Dp,
    val textAlign: TextAlign,
    val compositeTextColor: Boolean,
    val elevation: ButtonElevation
) {
    companion object {
        @Stable
        @Composable
        fun default(
            modifier: Modifier = Modifier,
            height: Dp = 56.dp,
            cornerRadius: Dp = AppTheme.size.cornerSm,
            textColorActive: Color = AppTheme.color.buttonText,
            textColorInactive: Color = AppTheme.color.buttonTextInactive,
            textStyle: TextStyle = AppTheme.typography.bodyLarge,
            backgroundColorActive: Color = AppTheme.color.buttonPrimary,
            backgroundColorInactive: Color = AppTheme.color.buttonPrimaryInactive,
            borderColorActive: Color = Color.Unspecified,
            borderColorInactive: Color = Color.Unspecified,
            borderWidth: Dp = Dp.Unspecified,
            textAlign: TextAlign = TextAlign.Start,
            compositeTextColor: Boolean = true,
            elevation: ButtonElevation = ButtonDefaults.elevatedButtonElevation()
        ) = remember {
            ButtonAppearance(
                height = height,
                textStyle = textStyle,
                cornerRadius = cornerRadius,
                textColorActive = textColorActive,
                textColorInactive = textColorInactive,
                backgroundColorActive = backgroundColorActive,
                backgroundColorInactive = backgroundColorInactive,
                borderColorInactive = borderColorInactive,
                compositeTextColor = compositeTextColor,
                borderColorActive = borderColorActive,
                borderWidth = borderWidth,
                textAlign = textAlign,
                elevation = elevation,
                modifier = modifier
            )
        }

        @Stable
        @Composable
        fun primaryMd() = default()

        @Stable
        @Composable
        fun positiveMd() = default(
            backgroundColorActive = AppTheme.color.positive,
            backgroundColorInactive = AppTheme.color.positive.disabled(),
            textColorActive = AppTheme.color.white,
            textColorInactive = AppTheme.color.white.disabled()
        )

        @Stable
        @Composable
        fun negativeMd() = default(
            backgroundColorActive = AppTheme.color.negative,
            backgroundColorInactive = AppTheme.color.negative.disabled(),
            textColorActive = AppTheme.color.white,
            textColorInactive = AppTheme.color.white.disabled()
        )

        @Stable
        @Composable
        fun secondaryMd() = default(
            backgroundColorActive = AppTheme.color.buttonSecondary,
            backgroundColorInactive = AppTheme.color.buttonSecondaryInactive
        )

        @Stable
        @Composable
        fun outlinedMd(
            backgroundColor: Color = Color.Unspecified
        ) = default(
            backgroundColorActive = backgroundColor,
            backgroundColorInactive = backgroundColor,
            borderColorActive = AppTheme.color.buttonPrimary,
            borderColorInactive = AppTheme.color.buttonPrimaryInactive,
            textColorActive = AppTheme.color.buttonPrimary,
            textColorInactive = AppTheme.color.buttonPrimaryInactive,
            textStyle = AppTheme.typography.bodyLarge,
            borderWidth = 1.dp,
            elevation = ButtonDefaults.buttonElevation()
        )

        @Stable
        @Composable
        fun primarySm(height: Dp = 40.dp) = default(
            textStyle = AppTheme.typography.bodyLarge,
            cornerRadius = AppTheme.size.cornerSm,
            textAlign = TextAlign.Center,
            height = height
        )

        @Stable
        @Composable
        fun secondarySm(height: Dp = 40.dp) = default(
            backgroundColorActive = AppTheme.color.buttonSecondary,
            backgroundColorInactive = AppTheme.color.buttonSecondaryInactive,
            textStyle = AppTheme.typography.bodyLarge,
            cornerRadius = AppTheme.size.cornerSm,
            textAlign = TextAlign.Center,
            height = height
        )

        @Stable
        @Composable
        fun primaryXs(height: Dp = 24.dp) = default(
            textStyle = AppTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            cornerRadius = 6.dp,
            height = height
        )

        @Stable
        @Composable
        fun secondaryXs(height: Dp = 24.dp) = default(
            backgroundColorActive = AppTheme.color.buttonSecondary,
            backgroundColorInactive = AppTheme.color.buttonSecondaryInactive,
            textStyle = AppTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            cornerRadius = 6.dp,
            height = height
        )

        @Stable
        @Composable
        fun textMd(
            textColorActive: Color = AppTheme.color.textPrimary,
            textStyle: TextStyle = AppTheme.typography.titleMedium,
            backgroundColorActive: Color = Color.Unspecified,
            backgroundColorInactive: Color = Color.Unspecified,
            height: Dp = 56.dp
        ) = default(
            backgroundColorActive = backgroundColorActive,
            backgroundColorInactive = backgroundColorInactive,
            textColorActive = textColorActive,
            textColorInactive = textColorActive.copy(alpha = 0.65f),
            elevation = ButtonDefaults.buttonElevation(),
            textStyle = textStyle,
            height = height
        )

        @Stable
        @Composable
        fun dottedMd(
            textColorActive: Color = AppTheme.color.textPrimary,
            height: Dp = 56.dp
        ) = default(
            backgroundColorActive = Color.Unspecified,
            backgroundColorInactive = Color.Unspecified,
            textColorActive = textColorActive,
            textColorInactive = textColorActive.copy(alpha = 0.65f),
            elevation = ButtonDefaults.buttonElevation(),
            height = height,
            modifier = Modifier.withDashedBorder(
                width = 1.dp,
                radius = 12.dp,
                color = AppTheme.color.textSecondary
            )
        )

        @Stable
        @Composable
        fun linkSm() = default(
            backgroundColorActive = AppTheme.color.textLink,
            backgroundColorInactive = AppTheme.color.textLink.disabled(),
            textStyle = AppTheme.typography.bodyMedium,
            textColorActive = AppTheme.color.white,
            cornerRadius = 6.dp,
            height = 28.dp
        )

        @Stable
        @Composable
        fun negativeSm() = default(
            backgroundColorActive = AppTheme.color.negative,
            backgroundColorInactive = AppTheme.color.negative.disabled(),
            textStyle = AppTheme.typography.bodyMedium,
            textColorActive = AppTheme.color.white,
            cornerRadius = 6.dp,
            height = 28.dp
        )

    }
}

fun LazyListScope.buttonItem(
    modifier: Modifier = Modifier,
    appearance: ButtonAppearance,
    @DrawableRes endIconRes: Int? = null,
    @StringRes titleRes: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    lazyItem(contentType = "ButtonBlock") {
        ButtonBlock(
            modifier = modifier,
            appearance = appearance,
            icon = endIconRes,
            text = titleRes,
            enabled = enabled,
            onClick = onClick
        )
    }
}

@Composable
@NonRestartableComposable
fun ButtonBlock(
    modifier: Modifier = Modifier,
    appearance: ButtonAppearance = ButtonAppearance.primaryMd(),
    enabled: Boolean = true,
    icon: Any? = null,
    text: Any? = null,
    onClick: () -> Unit
) {
    var textColor: Color
    val borderColor: Color
    val backgroundColor: Color
    when {
        enabled -> {
            textColor = appearance.textColorActive
            borderColor = appearance.borderColorActive
            backgroundColor = appearance.backgroundColorActive
        }

        else -> {
            textColor = appearance.textColorInactive
            borderColor = appearance.borderColorInactive
            backgroundColor = appearance.backgroundColorInactive
        }
    }
    if (appearance.compositeTextColor) {
        textColor = textColor.onColor(backgroundColor)
    }
    Button(
        modifier = modifier
            .then(appearance.modifier)
            .height(appearance.height),
        shape = RoundedCornerShape(appearance.cornerRadius),
        border = BorderStroke(appearance.borderWidth, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = appearance.backgroundColorActive,
            contentColor = appearance.textColorActive,
            disabledContainerColor = appearance.backgroundColorInactive,
            disabledContentColor = appearance.textColorInactive
        ),
        contentPadding = PaddingValues(horizontal = 8.dp),
        elevation = appearance.elevation,
        enabled = enabled,
        onClick = onClick
    ) {
        if (text != null) {
            TextBlock(
                style = appearance.textStyle,
                textAlign = appearance.textAlign,
                color = textColor,
                maxLines = 1,
                text = when (text) {
                    is String -> text
                    is Int -> stringResource(text)
                    else -> text.toString()
                }
            )
        }
        if (icon != null) {
            IconBlock(
                size = 24.dp,
                model = icon,
                tint = textColor
            )
        }
    }
}

@Preview
@Composable
private fun PreviewButtons() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ButtonBlock(
                appearance = ButtonAppearance.primaryMd(),
                text = "primaryMd",
                onClick = {}
            )
            ButtonBlock(
                appearance = ButtonAppearance.primaryMd(),
                text = "primaryMd",
                enabled = false,
                onClick = {}
            )

            ButtonBlock(
                appearance = ButtonAppearance.primarySm(),
                text = "primarySm",
                onClick = {}
            )
            ButtonBlock(
                appearance = ButtonAppearance.primarySm(),
                text = "primarySm",
                enabled = false,
                onClick = {}
            )

            ButtonBlock(
                appearance = ButtonAppearance.outlinedMd(),
                text = "outlinedMd",
                onClick = {}
            )
            ButtonBlock(
                appearance = ButtonAppearance.outlinedMd(),
                text = "outlinedMd",
                enabled = false,
                onClick = {}
            )

            ButtonBlock(
                appearance = ButtonAppearance.secondaryMd(),
                text = "secondaryMd",
                onClick = {}
            )
            ButtonBlock(
                appearance = ButtonAppearance.secondaryMd(),
                text = "secondaryMd",
                enabled = false,
                onClick = {}
            )

            ButtonBlock(
                appearance = ButtonAppearance.secondarySm(),
                text = "secondarySm",
                onClick = {}
            )
            ButtonBlock(
                appearance = ButtonAppearance.secondarySm(),
                text = "secondarySm",
                enabled = false,
                onClick = {}
            )

            ButtonBlock(
                appearance = ButtonAppearance.textMd(),
                text = "textMd",
                onClick = {}
            )
            ButtonBlock(
                appearance = ButtonAppearance.textMd(),
                text = "textMd",
                enabled = false,
                onClick = {}
            )
            ButtonBlock(
                appearance = ButtonAppearance.secondarySm(),
                icon = Icons.Default.MoreHoriz,
                text = "Icon",
                onClick = {}
            )
        }
    }
}