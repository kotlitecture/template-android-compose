@file:OptIn(ExperimentalFoundationApi::class)

package core.ui.block

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.ui.app.theme.AppTheme
import core.ui.misc.DisplayUnit
import core.ui.misc.extension.withClickable
import core.ui.misc.extension.withCornerRadius
import core.ui.misc.extension.withPaddingHorizontal16
import core.ui.misc.text.SpannableTextBuilder
import core.ui.misc.utils.FormatUtils

object AttrsBlockItem {
    @Immutable
    data class Text(
        val title: String,
        val caption: String,
        val getTitleColor: @Composable (() -> Color)? = null,
        val onClick: (() -> Unit)? = null
    )

    @Immutable
    data class FlexText(
        val title: String,
        val caption: String,
        val captionColor: @Composable (() -> Color)? = null
    )

    @Immutable
    data class Icon(
        val icon: Any,
        val noColor: Boolean = false,
        val onClick: () -> Unit,
        val onLongClick: (() -> Unit)? = null
    )
}

@Composable
@NonRestartableComposable
fun AttrsBlock(
    modifier: Modifier = Modifier,
    items: List<AttrsBlockItem.Text>,
    zeroStateItemsCount: Int = 7
) {
    val minWidth = remember { mutableStateOf(Dp.Unspecified) }
    val itemsRef = items.takeIf { it.isNotEmpty() } ?: (1..zeroStateItemsCount).map { null }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .onSizeChanged { minWidth.value = DisplayUnit.PX.toDp(it.height).dp },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (items.isEmpty()) {
            Spacer8()
        }
        itemsRef.forEachIndexed { idx, item ->
            if(item != null) {
                Spacer8()
            }
            Column(
                modifier = Modifier
                    .withCornerRadius(16.dp)
                    .widthIn(min = minWidth.value)
                    .withClickable(onClick = item?.onClick)
                    .padding(8.dp)
                    .withPlaceholder(
                        item == null, PlaceholderAppearance.default(
                        cornerRadius = 12.dp,
                        width = 64.dp
                    )),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                MediumTitlePrimary(
                    text = item?.title.orEmpty(),
                    color = item?.getTitleColor?.invoke() ?: AppTheme.color.textPrimary,
                    maxLines = 1
                )
                MediumTextPrimary(
                    text = item?.caption,
                    maxLines = 1,
                    color = AppTheme.color.textSecondary,
                    iconEnd = Icons.Default.Info.takeIf { item?.onClick != null },
                    iconColor = AppTheme.color.textHint,
                    iconSize = 8.dp
                )
            }
            Spacer8()
            if (idx < items.size - 1) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(20.dp)
                        .background(AppTheme.color.textHint)
                )
            }
        }
        if (items.isEmpty()) {
            Spacer8()
        }
    }
}

@Composable
@NonRestartableComposable
fun AttrsBlock(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    items: List<AttrsBlockItem.FlexText>
) {
    val builder = SpannableTextBuilder()
    items.forEachIndexed { index, item ->
        val titleColor: Color
        val captionColor: Color
        val titleWeight: FontWeight
        val captionWeight: FontWeight
        if (index % 2 == 0) {
            titleColor = AppTheme.color.textPrimary
            captionColor = AppTheme.color.textSecondary
            titleWeight = FontWeight.W500
            captionWeight = FontWeight.W400
        } else {
            titleColor = AppTheme.color.textSecondary
            captionColor = AppTheme.color.textPrimary
            titleWeight = FontWeight.W400
            captionWeight = FontWeight.W500
        }
        builder.append(
            item.title,
            SpanStyle(
                color = titleColor,
                fontSize = fontSize,
                fontWeight = titleWeight
            )
        )
        builder.appendSpace()
        builder.append(
            item.caption,
            SpanStyle(
                color = item.captionColor?.invoke() ?: captionColor,
                fontSize = fontSize,
                fontWeight = captionWeight
            )
        )
        if (index != items.size - 1) {
            builder.append(
                " ${FormatUtils.DOT} ",
                SpanStyle(
                    color = AppTheme.color.textPrimary,
                    fontWeight = FontWeight.W500,
                    fontSize = fontSize.times(1.5f),
                    baselineShift = BaselineShift(-0.2f)
                )
            )
        }
    }
    Text(
        modifier = modifier
            .withPaddingHorizontal16()
            .withPlaceholder(items.isEmpty()),
        text = builder.build(),
        fontSize = fontSize
    )
}

@Composable
@NonRestartableComposable
fun AttrsBlock(
    modifier: Modifier = Modifier,
    iconSize: Dp = 32.dp,
    items: List<AttrsBlockItem.Icon>
) {
    val itemsRef = remember(items) { items.takeIf { it.isNotEmpty() } ?: (1..3).map { null } }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsRef.forEach { item ->
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .combinedClickable(
                        onClick = item?.onClick ?: {},
                        onLongClick = item?.onLongClick
                    )
                    .padding(8.dp)
            ) {
                IconBlock(
                    modifier = Modifier.withPlaceholder(items.isEmpty()),
                    tint = if (item?.noColor == true) Color.Unspecified else AppTheme.color.iconPrimary,
                    model = item?.icon,
                    size = iconSize
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAttrsBlock() {
    AppTheme {
        Column(modifier = Modifier.background(AppTheme.color.surfacePrimary)) {
            Spacer24()
            AttrsBlock(
                items = (1..10).map {
                    AttrsBlockItem.Text(
                        title = "Item $it",
                        caption = "Caption $it",
                        onClick = {}
                    )
                }
            )
            Spacer24()
            AttrsBlock(
                items = (1..10).map {
                    AttrsBlockItem.Icon(
                        icon = Icons.Default.ContentCopy,
                        onClick = {}
                    )
                }
            )
            Spacer24()
            AttrsBlock(items = (1..10).map {
                AttrsBlockItem.FlexText(
                    title = "Item $it",
                    caption = "no",
                    captionColor = { AppTheme.color.negative }
                )
            })
            Spacer24()
        }
    }
}