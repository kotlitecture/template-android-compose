package core.ui.layout

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import core.data.state.StoreObject
import core.ui.app.theme.AppTheme
import core.ui.block.DividerBlock
import core.ui.block.SpacerFullHeight
import core.ui.block.SpacerNavigationBar
import core.ui.block.SpacerStatusBar
import core.ui.block.withAdvancedShadow
import core.ui.layout.item.lazyItem
import core.ui.layout.item.spacerNavigation
import core.ui.misc.extension.pxToDp
import core.ui.misc.extension.toPx
import core.ui.misc.extension.traceRecompose
import core.ui.misc.extension.withBackground
import core.ui.misc.extension.withTopCornerRadius
import core.ui.misc.extension.zIndexTop
import kotlinx.coroutines.launch
import java.io.Serializable
import java.lang.Float.min
import kotlin.math.max

@Immutable
data class TwoLayerLayoutAppearance(
    val statusColor: Color,
    val navigationColor: Color,
    val fullscreenOffsetExtra: Dp,
    val primaryContentColor: Color,
    val secondaryContentColor: Color,
    val secondaryContentShadow: Boolean,
    val secondaryContentShadowBlur: Dp,
    val secondaryContentMinimumHeight: Dp,
    val secondaryContainerCornerRadius: Dp,
    val itemsAlignment: Alignment.Horizontal
) {
    companion object {
        @Stable
        @Composable
        fun default(
            fullscreenOffsetExtra: Dp = 0.dp,
            statusColor: Color = AppTheme.color.statusPrimary,
            navigationColor: Color = AppTheme.color.surfacePrimary,
            primaryContentColor: Color = AppTheme.color.surfacePrimary,
            secondaryContentShadow: Boolean = false,
            secondaryContentShadowBlur: Dp = 8.dp,
            secondaryContentMinimumHeight: Dp = 0.dp,
            secondaryContentColor: Color = AppTheme.color.surfaceSecondary,
            secondaryContainerCornerRadius: Dp = AppTheme.size.cornerMd,
            itemsAlignment: Alignment.Horizontal = Alignment.CenterHorizontally
        ): TwoLayerLayoutAppearance {
            return remember {
                TwoLayerLayoutAppearance(
                    statusColor = statusColor,
                    itemsAlignment = itemsAlignment,
                    navigationColor = navigationColor,
                    primaryContentColor = primaryContentColor,
                    secondaryContentColor = secondaryContentColor,
                    fullscreenOffsetExtra = fullscreenOffsetExtra,
                    secondaryContentShadow = secondaryContentShadow,
                    secondaryContentShadowBlur = secondaryContentShadowBlur,
                    secondaryContentMinimumHeight = secondaryContentMinimumHeight,
                    secondaryContainerCornerRadius = secondaryContainerCornerRadius,
                )
            }
        }
    }
}

@Immutable
data class TwoLayerLayoutState(
    val listState: LazyListState,
    val fullscreen: MutableState<Boolean>,
    val appearance: TwoLayerLayoutAppearance,
    val statusColorState: MutableState<Color>,
    val fullscreenRegionState: MutableState<Int>,
    val fullscreenRegionInitialState: MutableState<Int>,
    val primaryHeaderProgress: MutableState<Float>,
    val primaryHeaderState: MutableState<PrimaryHeaderInfo>,
    val secondaryHeaderState: MutableState<SecondaryHeaderInfo>
) {
    companion object {
        @Stable
        @Composable
        fun default(
            listState: LazyListState = rememberLazyListState(),
            appearance: TwoLayerLayoutAppearance = TwoLayerLayoutAppearance.default(),
        ): TwoLayerLayoutState {
            val fullscreenRegionState = remember { mutableStateOf(0) }
            val fullscreenRegionInitialState = remember { mutableStateOf(0) }
            val fullscreen = rememberSaveable { mutableStateOf(false) }
            val primaryHeaderProgress = rememberSaveable { mutableStateOf(0f) }
            val primaryHeaderState = rememberSaveable { mutableStateOf(PrimaryHeaderInfo()) }
            val secondaryHeaderState = rememberSaveable { mutableStateOf(SecondaryHeaderInfo()) }
            val statusColor = remember {
                val color =
                    if (fullscreen.value) {
                        appearance.secondaryContentColor
                    } else {
                        appearance.statusColor
                    }
                mutableStateOf(color)
            }
            return remember {
                TwoLayerLayoutState(
                    listState = listState,
                    fullscreen = fullscreen,
                    appearance = appearance,
                    statusColorState = statusColor,
                    primaryHeaderState = primaryHeaderState,
                    secondaryHeaderState = secondaryHeaderState,
                    primaryHeaderProgress = primaryHeaderProgress,
                    fullscreenRegionState = fullscreenRegionState,
                    fullscreenRegionInitialState = fullscreenRegionInitialState
                )
            }
        }
    }
}

@Composable
fun TwoLayerLayout(
    dataKey: StoreObject<out Any>? = null,
    onRefresh: (() -> Unit)? = null,
    primaryContent: LazyListScope.() -> Unit,
    secondaryContent: LazyListScope.() -> Unit,
    state: TwoLayerLayoutState = TwoLayerLayoutState.default(),
    primaryHeader: @Composable (ColumnScope.(state: TwoLayerLayoutState) -> Unit)?,
    secondaryHeader: @Composable ColumnScope.(fullscreen: State<Boolean>) -> Unit,
    statusbarContent: @Composable () -> Unit = { SpacerStatusBar() },
    footerContent: (@Composable ColumnScope.() -> Unit)? = null,
) {
    traceRecompose("TwoLayerLayout", state)

    SwipeRefreshLayout(
        navigationColor = state.appearance.navigationColor,
        indicatorModifier = Modifier,
        onRefresh = onRefresh
    ) {
        val scope = rememberCoroutineScope()

        val listState = state.listState
        val appearance = state.appearance
        val fullscreenState = state.fullscreen
        val primaryHeaderState = state.primaryHeaderState
        val secondaryHeaderState = state.secondaryHeaderState
        val primaryHeaderProgress = state.primaryHeaderProgress

        val secondaryContentKey = "secondaryContentKey"
        val secondaryContentColor = appearance.secondaryContentColor

        val footerFillContentKey = "spacerFullHeightKey"

        val cornerRadius: Dp = appearance.secondaryContainerCornerRadius
        val cornerRadiusState = remember { mutableStateOf(cornerRadius) }
        val cornerRadiusAnim = animateDpAsState(
            cornerRadiusState.value, AppTheme.anim.tweenShort(),
            label = ""
        )

        val fullscreenOffsetExtra = remember { appearance.fullscreenOffsetExtra.toPx().toInt() }
        val fullscreenOffset = rememberSaveable { mutableStateOf(0) }
        val recomposeRequest = remember { mutableStateOf(0L) }
        val fullscreenRegionState = state.fullscreenRegionState

        FullscreenListener(state, dataKey, recomposeRequest)

        // TOOLBAR
        if (primaryHeader != null) {
            PrimaryHeader(state, statusbarContent, primaryHeader)
        }

        // MAIN CONTENT
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .nestedScroll(remember(recomposeRequest.value) {
                    object : NestedScrollConnection {

                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            if (fullscreenState.value && available.y < 0f) {
                                val info = listState.layoutInfo.visibleItemsInfo
                                    .find { it.key == footerFillContentKey }
                                if (info != null) {
                                    scope.launch {
                                        listState.stopScroll()
                                        recomposeRequest.value = System.currentTimeMillis()
                                    }
                                    return available
                                }
                            }
                            return Offset.Zero
                        }
                    }
                }),
            horizontalAlignment = appearance.itemsAlignment,
            state = listState
        ) {
            traceRecompose("TwoLayerLayout_content")
            if (primaryHeader != null) {
                lazyItem(contentType = "ToolbarSpacer") {
                    ToolbarSpacer(state)
                }
            }

            // PRIMARY CONTENT
            primaryContent(this)

            // SECONDARY HEADER
            lazyItem(
                sticky = true,
                key = secondaryContentKey,
                contentType = secondaryContentKey
            ) {
                Column(modifier = Modifier
                    .absoluteOffset {
                        if (fullscreenState.value) {
                            val offset = fullscreenOffset.value
                            IntOffset(0, offset)
                        } else {
                            IntOffset.Zero
                        }
                    }
                    .fillMaxWidth()
                    .heightIn(min = cornerRadius)
                    .let {
                        if (appearance.secondaryContentShadow) {
                            it.withAdvancedShadow(
                                color = AppTheme.color.shadowPrimary,
                                withBorderRadius = { cornerRadiusAnim.value },
                                withTopShadow = { !fullscreenState.value },
                                withBottomShadow = { false },
                                withBlurRadius = { appearance.secondaryContentShadowBlur },
                            )
                        } else {
                            it
                        }
                    }
                    .withTopCornerRadius { cornerRadiusAnim.value }
                    .clickable(enabled = false, onClick = remember { {} })
                    .withBackground { secondaryContentColor }
                    .zIndexTop()
                    .withScrollHandler(secondaryContentKey, listState) { info ->
                        val region = fullscreenRegionState.value
                        val fullscreenArea = region - fullscreenOffsetExtra
                        val fullscreen = info.offset < fullscreenArea
                        val offset = max(info.offset, fullscreenArea)
                        if (fullscreen) {
                            cornerRadiusState.value = 0.dp
                            val diff = fullscreenArea - info.offset
                            fullscreenOffset.value = diff
                            secondaryHeaderState.value = SecondaryHeaderInfo(
                                index = info.index, height = info.size, offset = offset
                            )
                        } else {
                            cornerRadiusState.value = cornerRadius
                            fullscreenOffset.value = 0
                            if (secondaryHeaderState.value.index != info.index) {
                                secondaryHeaderState.value = SecondaryHeaderInfo(
                                    index = info.index,
                                    height = info.size,
                                    offset = offset
                                )
                            }
                        }
                        fullscreenState.value = fullscreen
                        if (primaryHeaderState.value.height == 0) {
                            primaryHeaderState.value = PrimaryHeaderInfo(
                                height = fullscreenRegionState.value,
                                initialOffset = offset
                            )
                        } else {
                            val progress = primaryHeaderState.value.getHeaderProgress(offset)
                            primaryHeaderProgress.value = progress
                        }
                        this
                    }) {
                    secondaryHeader(this, fullscreenState)
                    SecondaryHeaderListener(state)
                }
            }

            // SECONDARY CONTENT
            secondaryContent(this)

            spacerNavigation(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(secondaryContentColor)
            )

            lazyItem(
                key = footerFillContentKey,
                contentType = footerFillContentKey
            ) {
                SpacerFullHeight(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(secondaryContentColor),
                    minus = appearance.secondaryContentMinimumHeight
                )
            }
        }

        // FOOTER CONTENT
        if (footerContent != null) {
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                footerContent()
                SpacerNavigationBar()
            }
        }
    }
}

@Composable
private fun PrimaryHeader(
    state: TwoLayerLayoutState,
    statusbarContent: @Composable () -> Unit = { SpacerStatusBar() },
    primaryHeader: @Composable (ColumnScope.(state: TwoLayerLayoutState) -> Unit),
) {
    val fullscreenRegionInitialState = state.fullscreenRegionInitialState
    val fullscreenRegionState = state.fullscreenRegionState
    val statusColorState = state.statusColorState
    val statusColorAnim = animateColorAsState(
        statusColorState.value, AppTheme.anim.tweenShort(),
        label = ""
    )
    Column(modifier = Modifier
        .fillMaxWidth()
        .zIndexTop()
        .withBackground { statusColorAnim.value }
        .clickable(enabled = false, onClick = {})
        .onSizeChanged {
            fullscreenRegionState.value = it.height
            if (fullscreenRegionInitialState.value == 0) {
                traceRecompose("ToolbarSpacer_onSizeChanged", fullscreenRegionInitialState.value)
                fullscreenRegionInitialState.value = it.height
            }
        }
    ) {
        traceRecompose("TwoLayerLayout#primaryHeader", state)
        statusbarContent()
        primaryHeader(state)
    }
}

@Composable
private fun ToolbarSpacer(state: TwoLayerLayoutState) {
    traceRecompose("ToolbarSpacer")
    Spacer(
        modifier = Modifier
            .height(state.fullscreenRegionInitialState.value.pxToDp())
            .fillMaxWidth()
    )
}

@Composable
private fun SecondaryHeaderListener(state: TwoLayerLayoutState) {
    if (state.appearance.secondaryContentShadow) {
        if (state.fullscreen.value) {
            DividerBlock()
        }
    }
}

@Composable
@NonRestartableComposable
private fun FullscreenListener(
    state: TwoLayerLayoutState,
    dataKey: StoreObject<out Any>?,
    recomposeRequest: MutableState<Long>
) {
    val extra = remember { 2.dp.toPx().toInt() }
    val scope = rememberCoroutineScope()
    val listState = state.listState
    val appearance = state.appearance
    val fullscreenState = state.fullscreen
    val statusColorState = state.statusColorState
    val secondaryHeaderState = state.secondaryHeaderState
    val initialized = remember { mutableStateOf(false) }
    LaunchedEffect(fullscreenState.value, dataKey?.asStateValue()) {
        val fullscreen = fullscreenState.value
        statusColorState.value = when {
            fullscreen -> appearance.secondaryContentColor
            else -> appearance.statusColor
        }
        if (fullscreen) {
            val info = secondaryHeaderState.value
            if (info.index > 0 && initialized.value) {
                scope.launch {
                    val scrollBy = -info.offset + extra
                    listState.scrollToItem(info.index, scrollBy)
                    recomposeRequest.value = System.currentTimeMillis()
                }
            }
        } else {
            initialized.value = true
        }
    }
}

@Immutable
data class PrimaryHeaderInfo(
    val height: Int = 0,
    var initialOffset: Int = 0
) : Serializable {

    private val headerStep = height / 100f
    private var prevProgress: Float = 0f

    fun getHeaderProgress(offset: Int): Float {
        val change = initialOffset - offset
        var progress = min(1f, change / headerStep / 100f)
        progress = max(0f, progress)
        initialOffset = max(offset, initialOffset)
        prevProgress = progress
        return progress
    }
}

@Immutable
data class SecondaryHeaderInfo(
    val index: Int = 0,
    val offset: Int = 0,
    val height: Int = 0
) : Serializable