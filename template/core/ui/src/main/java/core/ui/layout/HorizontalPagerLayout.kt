@file:OptIn(ExperimentalFoundationApi::class)

package core.ui.layout

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPagerIndicator
import core.ui.app.theme.AppTheme
import core.ui.block.SpacerNavigationBar
import core.ui.misc.DisplayUnit
import core.ui.misc.extension.traceRecompose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Immutable
class PagerLayoutState internal constructor(
    private val animate: Boolean,
    internal val state: PagerState,
    private val scope: CoroutineScope
) {

    fun isLast(): Boolean {
        return !state.canScrollForward
    }

    fun getCurrentPage(): Int {
        return state.currentPage
    }

    fun isFirst(): Boolean {
        return !state.canScrollBackward
    }

    fun onPrev() {
        onPage(getCurrentPage() - 1)
    }

    fun onNext() {
        onPage(getCurrentPage() + 1)
    }

    fun onPage(page: Int) {
        scope.launch {
            if (animate) {
                state.animateScrollToPage(page)
            } else {
                state.scrollToPage(page)
            }
        }
    }
}

@Stable
@Composable
fun <T> rememberPagerLayoutState(
    pages: List<T>,
    animate: Boolean = true,
    initialPage: Int = 0
): PagerLayoutState {
    val state = rememberPagerState(initialPage = initialPage, pageCount = pages::size)
    val scope = rememberCoroutineScope()
    return remember(pages.size) {
        PagerLayoutState(
            state = state,
            scope = scope,
            animate = animate
        )
    }
}

@Composable
fun <T> HorizontalPagerLayout(
    modifier: Modifier = Modifier,
    pages: List<T>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    indicatorsBackground: Color = AppTheme.color.statusPrimary,
    toolbarBackground: Color = AppTheme.color.statusPrimary,
    state: PagerLayoutState = rememberPagerLayoutState(pages),
    withBeyondBoundsPageCount: Int = pages.size,
    withIndicatorsExtraSpace: Boolean = true,
    winIndicatorsPaddings: Dp = 16.dp,
    withIndicators: Boolean = false,
    withBackHandler: Boolean = true,
    onPageChanged: ((index: Int) -> Unit)? = null,
    toolbar: (@Composable ColumnScope.(page: T?) -> Unit)? = null,
    pageContent: @Composable (index: Int, page: T?) -> Unit
) {
    traceRecompose("HorizontalPagerLayout")
    Box(Modifier.fillMaxSize()) {
        val indicatorsHeight = remember { mutableStateOf(0.dp) }
        val toolbarHeight = remember { mutableStateOf(0.dp) }
        val canRenderContent = toolbar == null || toolbarHeight.value.value > 0f

        if (canRenderContent) {
            if (pages.size > 1) {
                HorizontalPager(
                    key = { it },
                    state = state.state,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxSize(),
                    beyondBoundsPageCount = withBeyondBoundsPageCount,
                    contentPadding = contentPadding
                ) { pageIndex ->
                    traceRecompose("HorizontalPagerLayout_pageIndex", pageIndex, withBeyondBoundsPageCount)
                    PageContent(
                        modifier = modifier,
                        toolbarHeight = toolbarHeight.value,
                        indicatorsHeight = indicatorsHeight.value,
                        page = pages.getOrNull(pageIndex),
                        content = pageContent,
                        index = pageIndex
                    )
                }
            } else if (pages.isNotEmpty()) {
                PageContent(
                    modifier = modifier,
                    toolbarHeight = toolbarHeight.value,
                    indicatorsHeight = indicatorsHeight.value,
                    page = pages.firstOrNull(),
                    content = pageContent,
                    index = 0
                )
            }
        }

        if (toolbar != null) {
            ToolbarBlock(
                pages = pages,
                state = state,
                toolbarHeight = toolbarHeight,
                toolbarBackground = toolbarBackground,
                toolbar = toolbar
            )
        }

        if (withIndicators && pages.size > 1) {
            traceRecompose("HorizontalPagerLayout_indicators")
            Column(modifier = Modifier
                .onSizeChanged { indicatorsHeight.value = DisplayUnit.PX.toDp(it.height).dp }
                .background(indicatorsBackground)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()) {
                HorizontalPagerIndicator(
                    pagerState = state.state,
                    pageCount = pages.size,
                    modifier = Modifier
                        .padding(winIndicatorsPaddings)
                        .align(CenterHorizontally),
                    inactiveColor = AppTheme.color.dividerPrimary,
                    activeColor = AppTheme.color.buttonPrimary,
                )
                if (withIndicatorsExtraSpace) {
                    SpacerNavigationBar()
                }
            }
        }
    }

    if (withBackHandler) {
        BackHandlerListener(state)
    }

    if (onPageChanged != null) {
        PageChangeListener(state, onPageChanged)
    }
}

@Composable
private fun <T> ToolbarBlock(
    pages: List<T>,
    state: PagerLayoutState,
    toolbarHeight: MutableState<Dp>,
    toolbarBackground: Color = AppTheme.color.statusPrimary,
    toolbar: (@Composable ColumnScope.(page: T?) -> Unit)
) {
    traceRecompose("HorizontalPagerLayout_toolbar")
    Column(modifier = Modifier
        .onSizeChanged { toolbarHeight.value = DisplayUnit.PX.toDp(it.height).dp }
        .background(toolbarBackground)
        .fillMaxWidth()) {
        toolbar.invoke(this, pages.getOrNull(state.getCurrentPage()))
    }
}

@Composable
private fun PageChangeListener(state: PagerLayoutState, onPageChanged: ((index: Int) -> Unit)) {
    traceRecompose("HorizontalPagerLayout_PageChangeListener")
    LaunchedEffect(state.getCurrentPage()) {
        onPageChanged(state.getCurrentPage())
    }
}

@Composable
private fun BackHandlerListener(state: PagerLayoutState) {
    traceRecompose("HorizontalPagerLayout_BackHandler")
    if (!state.isFirst()) {
        BackHandler(onBack = state::onPrev)
    }
}

@Composable
private fun <T> BoxScope.PageContent(
    modifier: Modifier,
    index: Int,
    page: T?,
    toolbarHeight: Dp,
    indicatorsHeight: Dp,
    content: @Composable (index: Int, page: T?) -> Unit
) {
    if (toolbarHeight > 0.dp || indicatorsHeight > 0.dp) {
        Column(
            modifier = modifier
                .let {
                    if (toolbarHeight.value > 0f) {
                        it.verticalScroll(rememberScrollState())
                    } else {
                        it
                    }
                }
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            horizontalAlignment = CenterHorizontally
        ) {
            if (toolbarHeight.value > 0f) {
                Spacer(modifier = Modifier.height(toolbarHeight))
            }
            content(index, page)
            if (indicatorsHeight.value > 0f) {
                Spacer(modifier = Modifier.height(indicatorsHeight))
            }
        }
    } else {
        content(index, page)
    }
}