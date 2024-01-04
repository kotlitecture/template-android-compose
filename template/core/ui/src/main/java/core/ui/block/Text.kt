package core.ui.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme

@Composable
@NonRestartableComposable
fun MediumHeaderPrimary(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.color.textPrimary,
    fontSize: TextUnit = TextUnit.Unspecified,
    minWidth: Dp = Dp.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = 1,
    text: String?,
    iconColor: Color = color,
    iconStart: Any? = null,
    iconEnd: Any? = null,
    iconSize: Dp = AppTheme.size.iconXs
) {
    TextBlock(
        modifier = modifier,
        minWidth = minWidth,
        text = text,
        maxLines = maxLines,
        textAlign = textAlign,
        fontWeight = FontWeight.W600,
        style = AppTheme.typography.headlineMedium,
        fontSize = fontSize,
        color = color,
        iconColor = iconColor,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = iconSize
    )
}

@Composable
@NonRestartableComposable
fun SmallHeaderPrimary(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.color.textPrimary,
    fontSize: TextUnit = TextUnit.Unspecified,
    minWidth: Dp = Dp.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = 1,
    text: String?,
    iconColor: Color = color,
    iconStart: Any? = null,
    iconEnd: Any? = null,
    iconSize: Dp = AppTheme.size.iconXs,
    iconEndModifier: Modifier = Modifier
) {
    TextBlock(
        modifier = modifier,
        minWidth = minWidth,
        text = text,
        maxLines = maxLines,
        textAlign = textAlign,
        fontWeight = FontWeight.W600,
        style = AppTheme.typography.headlineSmall,
        fontSize = fontSize,
        color = color,
        iconColor = iconColor,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = iconSize,
        iconEndModifier = iconEndModifier
    )
}

@Composable
@NonRestartableComposable
fun MediumTitlePrimary(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.color.textPrimary,
    fontSize: TextUnit = TextUnit.Unspecified,
    minWidth: Dp = Dp.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = 1,
    text: String?,
    iconColor: Color = color,
    iconStart: Any? = null,
    iconEnd: Any? = null,
    iconSize: Dp = AppTheme.size.iconXs,
    iconEndModifier: Modifier = Modifier
) {
    TextBlock(
        modifier = modifier,
        minWidth = minWidth,
        text = text,
        maxLines = maxLines,
        textAlign = textAlign,
        fontWeight = FontWeight.W500,
        style = AppTheme.typography.titleMedium,
        fontSize = fontSize,
        color = color,
        iconColor = iconColor,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = iconSize,
        iconEndModifier = iconEndModifier
    )
}

@Composable
@NonRestartableComposable
fun MediumTitleSecondary(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.color.textSecondary,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    minWidth: Dp = Dp.Unspecified,
    maxLines: Int = 1,
    text: String?,
    iconColor: Color = color,
    iconStart: Any? = null,
    iconEnd: Any? = null,
    iconSize: Dp = AppTheme.size.iconXs
) {
    TextBlock(
        modifier = modifier,
        minWidth = minWidth,
        text = text,
        maxLines = maxLines,
        textAlign = textAlign,
        fontWeight = FontWeight.W400,
        style = AppTheme.typography.titleMedium,
        fontSize = fontSize,
        color = color,
        iconColor = iconColor,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = iconSize
    )
}

@Composable
@NonRestartableComposable
fun MediumTextPrimary(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.color.textPrimary,
    fontSize: TextUnit = TextUnit.Unspecified,
    minWidth: Dp = Dp.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = 1,
    text: String?,
    iconColor: Color = color,
    iconStart: Any? = null,
    iconEnd: Any? = null,
    iconSize: Dp = AppTheme.size.iconXs,
    iconEndModifier: Modifier = Modifier
) {
    TextBlock(
        modifier = modifier,
        minWidth = minWidth,
        text = text,
        maxLines = maxLines,
        textAlign = textAlign,
        fontWeight = FontWeight.W400,
        style = AppTheme.typography.bodyMedium,
        fontSize = fontSize,
        color = color,
        iconColor = iconColor,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = iconSize,
        iconEndModifier = iconEndModifier
    )
}

@Composable
@NonRestartableComposable
fun SmallTextPrimary(
    modifier: Modifier = Modifier,
    color: Color? = null,
    minWidth: Dp = Dp.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = 1,
    text: String?,
    iconColor: Color? = null,
    iconStart: Any? = null,
    iconEnd: Any? = null,
    iconSize: Dp = AppTheme.size.iconXs
) {
    val textColor = color ?: AppTheme.color.textPrimary
    TextBlock(
        modifier = modifier,
        minWidth = minWidth,
        text = text,
        maxLines = maxLines,
        textAlign = textAlign,
        fontWeight = FontWeight.W600,
        style = AppTheme.typography.labelLarge,
        iconColor = iconColor ?: textColor,
        color = textColor,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = iconSize
    )
}

@Composable
@NonRestartableComposable
fun SmallTextSecondary(
    modifier: Modifier = Modifier,
    color: Color? = null,
    minWidth: Dp = Dp.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = 1,
    text: String?,
    iconColor: Color? = null,
    iconStart: Any? = null,
    iconEnd: Any? = null,
    iconSize: Dp = AppTheme.size.iconXs
) {
    val textColor = color ?: AppTheme.color.textSecondary
    TextBlock(
        modifier = modifier,
        minWidth = minWidth,
        text = text,
        maxLines = maxLines,
        fontWeight = FontWeight.W400,
        style = AppTheme.typography.labelLarge,
        textAlign = textAlign,
        color = textColor,
        iconColor = iconColor ?: textColor,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = iconSize
    )
}

@Composable
@NonRestartableComposable
fun TinyTextPrimary(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.color.textPrimary,
    textAlign: TextAlign? = null,
    minWidth: Dp = Dp.Unspecified,
    maxLines: Int = 1,
    text: String?,
    iconColor: Color = color,
    iconStart: Any? = null,
    iconEnd: Any? = null,
    iconSize: Dp = AppTheme.size.iconXs
) {
    TextBlock(
        modifier = modifier,
        minWidth = minWidth,
        text = text,
        maxLines = maxLines,
        textAlign = textAlign,
        style = AppTheme.typography.labelMedium,
        color = color,
        iconColor = iconColor,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = iconSize
    )
}

@Composable
@NonRestartableComposable
fun TinyTextSecondary(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.color.textSecondary,
    textAlign: TextAlign? = null,
    minWidth: Dp = Dp.Unspecified,
    maxLines: Int = 1,
    text: String?,
    iconColor: Color = color,
    iconStart: Any? = null,
    iconEnd: Any? = null,
    iconSize: Dp = AppTheme.size.iconXs,
    iconStartModifier: Modifier = Modifier,
    iconEndModifier: Modifier = Modifier
) {
    TextBlock(
        modifier = modifier,
        minWidth = minWidth,
        text = text,
        maxLines = maxLines,
        textAlign = textAlign,
        style = AppTheme.typography.labelSmall,
        color = color,
        iconColor = iconColor,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = iconSize,
        iconStartModifier = iconStartModifier,
        iconEndModifier = iconEndModifier
    )
}

@Composable
@NonRestartableComposable
fun TextBlock(
    modifier: Modifier = Modifier,
    text: String?,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    fontSize: TextUnit? = null,
    placeholder: Boolean = text == null,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = 1,
    iconColor: Color = color,
    iconStart: Any? = null,
    iconEnd: Any? = null,
    minWidth: Dp = Dp.Unspecified,
    iconSize: Dp = AppTheme.size.iconXs,
    iconStartModifier: Modifier = Modifier,
    iconEndModifier: Modifier = Modifier
) {
    val textSize = fontSize ?: style.fontSize
    if (iconStart != null || iconEnd != null) {
        Row(
            modifier = modifier
                .wrapContentHeight()
                .widthIn(min = minWidth)
                .withPlaceholder(
                    visible = placeholder,
                    appearance = PlaceholderAppearance.default(
                        width = minWidth.takeIf { it != Dp.Unspecified }
                    )),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (iconStart != null) {
                IconBlock(
                    modifier = iconStartModifier,
                    model = iconStart,
                    tint = iconColor,
                    size = iconSize
                )
            }
            Text(
                text = text.orEmpty(),
                fontWeight = fontWeight,
                textAlign = textAlign,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                style = style,
                color = color,
                softWrap = maxLines > 1,
                fontSize = textSize
            )
            if (iconEnd != null) {
                IconBlock(
                    modifier = iconEndModifier,
                    model = iconEnd,
                    tint = iconColor,
                    size = iconSize
                )
            }
        }
    } else {
        Text(
            modifier = modifier
                .widthIn(min = minWidth)
                .withPlaceholder(
                    visible = placeholder,
                    appearance = PlaceholderAppearance.default(
                        width = minWidth.takeIf { it != Dp.Unspecified }
                    )),
            text = text.orEmpty(),
            fontWeight = fontWeight,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            softWrap = maxLines > 1,
            style = style,
            color = color,
            fontSize = textSize
        )
    }
}