package core.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme
import core.ui.block.MediumTextPrimary
import core.ui.block.PlaceholderAppearance
import core.ui.block.withPlaceholder
import core.ui.misc.extension.traceRecompose
import core.ui.misc.extension.withClickable
import core.ui.misc.extension.withCornerRadius

@Immutable
data class ChipsLayoutAppearance(
    val selectedContainerColor: Color,
    val selectedLabelColor: Color,
    val containerColor: Color,
    val labelColor: Color,
    val cornerRadius: Dp,
    val textPadding: Dp,
    val height: Dp,
    val width: Dp,
    val space: Dp
) {
    companion object {
        @Stable
        @Composable
        fun secondary(
            selectedContainerColor: Color = AppTheme.color.buttonPrimary,
            selectedLabelColor: Color = AppTheme.color.buttonText,
            containerColor: Color = AppTheme.color.buttonSecondary,
            labelColor: Color = AppTheme.color.buttonTextInactive,
            cornerRadius: Dp = AppTheme.size.cornerSm,
            width: Dp = Dp.Unspecified,
            textPadding: Dp = 0.dp,
            height: Dp = 28.dp,
            space: Dp = 8.dp
        ): ChipsLayoutAppearance {
            return remember {
                ChipsLayoutAppearance(
                    selectedContainerColor = selectedContainerColor,
                    selectedLabelColor = selectedLabelColor,
                    containerColor = containerColor,
                    cornerRadius = cornerRadius,
                    textPadding = textPadding,
                    labelColor = labelColor,
                    height = height,
                    width = width,
                    space = space
                )
            }
        }

        @Stable
        @Composable
        fun primary(): ChipsLayoutAppearance = secondary(textPadding = 12.dp)
    }

}

@Immutable
data class ChipItems<T>(val items: List<T>)

@Composable
fun <T> ChipsLayout(
    modifier: Modifier = Modifier,
    appearance: ChipsLayoutAppearance = ChipsLayoutAppearance.primary(),
    items: List<T>,
    onClick: (item: T) -> Unit,
    placeholder: Boolean = false,
    itemLabelProvider: @Composable (item: T) -> String,
    selectedItemsProvider: () -> Collection<T>
) {
    traceRecompose("ChipsLayout", items)
    val selectedItemState = remember { mutableStateOf(selectedItemsProvider().firstOrNull()) }
    ScrollableRowLayout(
        modifier = modifier,
        itemsState = remember { mutableStateOf(items) },
        appearance = ScrollableRowLayoutAppearance.default(
            withEdgePadding = appearance.space.times(2f)
        ),
        selectedItem = selectedItemState,
        itemContent = { item, selected ->
            ChipItem(
                modifier = Modifier.padding(end = appearance.space),
                itemLabelProvider = itemLabelProvider,
                placeholder = placeholder,
                appearance = appearance,
                selected = selected,
                onClick = onClick,
                item = item
            )
        }
    )
    SelectedItemListener(selectedItemsProvider, selectedItemState)
}

@Composable
private fun <T> SelectedItemListener(
    selectedItemsProvider: () -> Collection<T>,
    selectedItemState: MutableState<T?>
) {
    val selection = selectedItemsProvider()
    LaunchedEffect(selection) {
        selectedItemState.value = selection.firstOrNull()
    }
}

@Composable
private fun <T> ChipItem(
    modifier: Modifier = Modifier,
    item: T,
    placeholder: Boolean,
    appearance: ChipsLayoutAppearance,
    onClick: (item: T) -> Unit,
    itemLabelProvider: @Composable (item: T) -> String,
    selected: Boolean,
) {
    val containerColor: Color
    val textColor: Color
    when {
        selected -> {
            containerColor = appearance.selectedContainerColor
            textColor = appearance.selectedLabelColor
        }

        else -> {
            containerColor = appearance.containerColor
            textColor = appearance.labelColor
        }
    }
    Row(modifier = modifier
        .withPlaceholder(
            placeholder,
            PlaceholderAppearance.default(cornerRadius = AppTheme.size.cornerSm)
        )
        .withCornerRadius(appearance.cornerRadius)
        .withClickable { onClick.invoke(item) }
        .background(containerColor)
        .height(appearance.height)
        .widthIn(appearance.width),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MediumTextPrimary(
            modifier = Modifier.padding(horizontal = appearance.textPadding),
            text = itemLabelProvider.invoke(item),
            color = textColor
        )
    }
}