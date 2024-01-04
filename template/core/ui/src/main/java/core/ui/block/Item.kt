@file:OptIn(ExperimentalFoundationApi::class)

package core.ui.block

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme
import core.ui.misc.extension.vibrateHaptic

@Immutable
data class ItemData(
    val icon: Any? = null,
    val hasIcon: Boolean = true,
    val hasPrimaryLabel: Boolean = true,
    val hasSecondaryLabel: Boolean = true,
    val hasDivider: Boolean = true,
    val primaryText: String? = null,
    val primaryTextColor: Color? = null,
    val secondaryText: String? = null,
    val secondaryTextColor: Color? = null,
    val primaryLabel: String? = null,
    val primaryLabelIcon: Any? = null,
    val primaryLabelColor: Color? = null,
    val secondaryLabel: String? = null,
    val secondaryLabelIcon: Any? = null,
    val secondaryLabelColor: Color? = null,
    val iconEnd: Any? = null
)

@Composable
@NonRestartableComposable
fun ItemBlock(
    modifier: Modifier = Modifier,
    value: ItemData? = null,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    iconContent: @Composable (value: ItemData?) -> Unit = {
        RoundedImageBlock(
            modifier = Modifier,
            model = it?.icon,
            size = AppTheme.size.iconLg
        )
    },
    iconEnd: @Composable (value: ItemData?) -> Unit = {
        if (it?.iconEnd != null) {
            IconBlock(
                model = it.iconEnd,
                tint = AppTheme.color.textSecondary,
                size = 24.dp
            )
        }
    },
    primaryText: @Composable RowScope.(value: ItemData?) -> Unit = {
        SmallTextPrimary(
            modifier = Modifier.weight(1f),
            color = it?.primaryTextColor,
            text = it?.primaryText,
            minWidth = 72.dp
        )
    },
    secondaryText: @Composable RowScope.(value: ItemData?) -> Unit = {
        SmallTextSecondary(
            modifier = Modifier.weight(1f),
            color = it?.secondaryTextColor,
            text = it?.secondaryText,
            minWidth = 120.dp
        )
    },
    primaryLabel: @Composable RowScope.(value: ItemData?) -> Unit = {
        SmallTextPrimary(
            iconEnd = it?.primaryLabelIcon,
            color = it?.primaryLabelColor,
            textAlign = TextAlign.End,
            text = it?.primaryLabel,
            minWidth = 32.dp
        )
    },
    secondaryLabel: @Composable RowScope.(value: ItemData?) -> Unit = {
        SmallTextSecondary(
            iconEnd = it?.secondaryLabelIcon,
            color = it?.secondaryLabelColor,
            textAlign = TextAlign.End,
            text = it?.secondaryLabel,
            minWidth = 48.dp
        )
    },
    dividerContent: @Composable (value: ItemData?) -> Unit = {
        DividerBlock()
    }
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                enabled = onClick != null,
                onClick = { onClick?.invoke() },
                onLongClick = onLongClick?.let {
                    {
                        onLongClick.invoke()
                        context.vibrateHaptic()
                    }
                }
            )
            .let {
                if (selected) {
                    it.background(AppTheme.color.itemSelected)
                } else {
                    it
                }
            }
    ) {
        Spacer16()
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer16()

            // ICON
            if (value == null || value.hasIcon) {
                iconContent(value)
                Spacer8()
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                // PRIMARY LINE
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    primaryText(value)
                    if (value?.primaryText == null) {
                        Spacer(modifier = Modifier.width(120.dp))
                    }
                    if (value?.hasPrimaryLabel != false) {
                        Spacer8()
                        primaryLabel(value)
                    }
                }

                // SECONDARY LINE
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    secondaryText(value)
                    if (value?.secondaryText == null) {
                        Spacer(modifier = Modifier.width(100.dp))
                    }
                    if (value?.hasSecondaryLabel != false) {
                        Spacer8()
                        secondaryLabel(value)
                    }
                }
            }

            // ICON END
            if (value?.iconEnd != null) {
                Spacer8()
                iconEnd(value)
            }

            Spacer16()
        }
        Spacer16()
        if (value != null && value.hasDivider) {
            dividerContent(value)
        }
    }
}