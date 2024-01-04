package core.ui.layout

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.essentials.misc.extensions.takeIfIndex
import core.ui.app.theme.AppTheme
import core.ui.layout.TabRowDefaults.tabIndicatorOffset
import core.ui.misc.extension.traceRecompose
import core.ui.misc.extension.withCornerRadius
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Immutable
data class ScrollableRowLayoutAppearance(
    val withContainerColor: Color,
    val withIndicatorColor: Color,
    val withContentColor: Color,
    val withEdgePadding: Dp,
    val withMinTabWidth: Dp,
    val withIndicatorHeight: Dp,
    val withIndicators: Boolean,
    val withDivider: Boolean,
    val withTabPaddingStart: Dp,
    val withTabPaddingEnd: Dp,
    val withAnimationSpec: AnimationSpec<Dp>
) {
    companion object {
        @Stable
        @Composable
        fun default(
            withIndicatorColor: Color = AppTheme.color.textPrimary,
            withAnimationDuration: Int = AppTheme.anim.durationShort,
            withIndicators: Boolean = false,
            withTabPaddingStart: Dp = 0.dp,
            withTabPaddingEnd: Dp = 0.dp,
            withEdgePadding: Dp = 8.dp,
            withDivider: Boolean = false
        ): ScrollableRowLayoutAppearance {
            return remember {
                ScrollableRowLayoutAppearance(
                    withTabPaddingStart = withTabPaddingStart,
                    withIndicatorColor = withIndicatorColor,
                    withContainerColor = Color.Unspecified,
                    withTabPaddingEnd = withTabPaddingEnd,
                    withContentColor = Color.Unspecified,
                    withEdgePadding = withEdgePadding,
                    withIndicators = withIndicators,
                    withIndicatorHeight = 3.dp,
                    withMinTabWidth = 24.dp,
                    withDivider = withDivider,
                    withAnimationSpec = tween(
                        durationMillis = withAnimationDuration,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }
}

@Immutable
data class ItemPosition(val left: Dp, val width: Dp) {
    val right: Dp = left + width
}

@Composable
@NonRestartableComposable
fun <I> ScrollableRowLayout(
    selectedItem: State<I?>,
    itemsState: State<List<I>>,
    modifier: Modifier = Modifier,
    appearance: ScrollableRowLayoutAppearance = ScrollableRowLayoutAppearance.default(),
    indicatorStartPadding: (item: I) -> Dp = { 0.dp },
    indicator: @Composable (selectedIndex: Int, itemPositions: List<ItemPosition>) -> Unit = @Composable { selectedIndex, positions ->
        val paddingStart = itemsState.value.getOrNull(selectedIndex)?.let(indicatorStartPadding)
            ?: 0.dp
        TabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(
                positions.getOrElse(selectedIndex) { positions.first() },
                appearance.withAnimationSpec
            ),
            paddingStart = paddingStart,
            height = appearance.withIndicatorHeight,
            color = appearance.withIndicatorColor
        )
    },
    divider: @Composable () -> Unit = @Composable { Divider() },
    itemContent: @Composable (item: I, selected: Boolean) -> Unit
) {
    val items = itemsState.value
    traceRecompose("ScrollableRowLayout", items)
    Surface(
        modifier = modifier,
        color = appearance.withContainerColor,
        contentColor = appearance.withContentColor
    ) {
        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()
        val scrollableItems = remember(items, selectedItem.value) {
            ScrollableItems(
                items = items,
                scrollState = scrollState,
                selectedItem = selectedItem.value,
                coroutineScope = coroutineScope
            )
        }
        SubcomposeLayout(
            Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.CenterStart)
                .horizontalScroll(scrollState)
                .selectableGroup()
                .clipToBounds()
        ) { constraints ->
            val minTabWidth = appearance.withMinTabWidth.roundToPx()
            val padding = appearance.withEdgePadding.roundToPx()

            val itemMeasures = subcompose(TabSlots.Tabs) {
                scrollableItems.items.forEach { item ->
                    itemContent.invoke(item, scrollableItems.selectedItem == item)
                }
            }

            val layoutHeight = itemMeasures.fold(initial = 0) { curr, measurable ->
                maxOf(curr, measurable.maxIntrinsicHeight(Constraints.Infinity))
            }

            val itemConstraints = constraints.copy(minWidth = minTabWidth, minHeight = layoutHeight)
            val itemPlaces = itemMeasures.map { it.measure(itemConstraints) }

            val layoutWidth = itemPlaces.fold(initial = padding * 2) { curr, measurable ->
                curr + measurable.width
            }

            layout(layoutWidth, layoutHeight) {
                val positions = mutableListOf<ItemPosition>()
                var left = padding
                itemPlaces.forEach {
                    it.placeRelative(left, 0)
                    positions.add(
                        ItemPosition(
                            left = left.toDp(),
                            width = it.width.toDp()
                        )
                    )
                    left += it.width
                }

                if (appearance.withDivider) {
                    subcompose(TabSlots.Divider, divider)
                        .forEach {
                            val placeable = it.measure(
                                constraints.copy(
                                    minHeight = 0,
                                    minWidth = layoutWidth,
                                    maxWidth = layoutWidth
                                )
                            )
                            placeable.placeRelative(0, layoutHeight - placeable.height)
                        }
                }

                if (appearance.withIndicators) {
                    subcompose(TabSlots.Indicator) {
                        indicator(
                            scrollableItems.selectedItemIndex,
                            positions
                        )
                    }
                        .forEach {
                            it.measure(Constraints.fixed(layoutWidth, layoutHeight))
                                .placeRelative(0, 0)
                        }
                }

                scrollableItems.onLaidOut(
                    edgeOffset = padding,
                    positions = positions,
                    density = this@SubcomposeLayout
                )
            }
        }
    }
}

private object TabRowDefaults {
    @Composable
    fun Indicator(
        modifier: Modifier = Modifier,
        paddingStart: Dp = 0.dp,
        height: Dp,
        color: Color
    ) {
        Box(modifier.height(height), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = paddingStart)
                    .width(24.dp)
                    .background(color = color)
                    .withCornerRadius(1.dp)
            )
        }
    }

    @Stable
    fun Modifier.tabIndicatorOffset(
        currentItemPosition: ItemPosition,
        animationSpec: AnimationSpec<Dp>
    ): Modifier = composed(
        inspectorInfo = debugInspectorInfo {
            name = "tabIndicatorOffset"
            value = currentItemPosition
        }
    ) {
        val currentTabWidth by animateDpAsState(
            targetValue = currentItemPosition.width,

            animationSpec = animationSpec, label = ""
        )
        val indicatorOffset by animateDpAsState(
            targetValue = currentItemPosition.left,
            animationSpec = animationSpec, label = ""
        )
        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorOffset)
            .width(currentTabWidth)
    }
}

@Immutable
private enum class TabSlots {
    Tabs,
    Divider,
    Indicator
}

@Immutable
private data class ScrollableItems<I>(
    val items: List<I>,
    val selectedItem: I?,
    val scrollState: ScrollState,
    val coroutineScope: CoroutineScope,
    val selectedItemIndex: Int = selectedItem?.let { items.indexOf(it) }?.takeIfIndex() ?: 0
) {
    private var lastItemIndex: Int? = null
    private var job: Job? = null

    fun onLaidOut(
        edgeOffset: Int,
        density: Density,
        positions: List<ItemPosition>
    ) {
        if (lastItemIndex != selectedItemIndex) {
            lastItemIndex = selectedItemIndex
            positions.getOrNull(selectedItemIndex)?.let {
                val calculatedOffset = it.calculateTabOffset(density, edgeOffset, positions)
                if (scrollState.value != calculatedOffset) {
                    job?.cancel()
                    job = coroutineScope.launch {
                        scrollState.animateScrollTo(calculatedOffset)
                    }
                }
            }
        }
    }

    @Stable
    private fun ItemPosition.calculateTabOffset(
        density: Density,
        edgeOffset: Int,
        itemPositions: List<ItemPosition>
    ): Int = with(density) {
        val totalTabRowWidth = itemPositions.last().right.roundToPx() + edgeOffset
        val visibleWidth = totalTabRowWidth - scrollState.maxValue
        val tabOffset = left.roundToPx()
        val scrollerCenter = visibleWidth / 2
        val tabWidth = width.roundToPx()
        val centeredTabOffset = tabOffset - (scrollerCenter - tabWidth / 2)
        val availableSpace = (totalTabRowWidth - visibleWidth).coerceAtLeast(0)
        return centeredTabOffset.coerceIn(0, availableSpace)
    }
}