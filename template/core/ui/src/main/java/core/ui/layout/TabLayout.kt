package core.ui.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.data.state.StoreObject
import core.ui.app.theme.AppTheme
import core.ui.block.DividerBlock
import core.ui.block.Spacer4
import core.ui.block.Spacer8
import core.ui.block.SpacerStatusBar
import core.ui.block.TabBlock
import kotlinx.coroutines.launch

@Stable
@Immutable
data class TabLayoutAppearance(
    val withTabIndicators: Boolean = true,
    val withBackHandler: Boolean = true,
    val withCornerRadius: Dp
) {
    companion object {
        @Stable
        @Composable
        fun default(
            withCornerRadius: Dp = AppTheme.size.cornerMd,
            withTabIndicators: Boolean = true,
            withBackHandler: Boolean = true
        ) = remember {
            TabLayoutAppearance(
                withTabIndicators = withTabIndicators,
                withCornerRadius = withCornerRadius,
                withBackHandler = withBackHandler
            )
        }
    }
}

@Immutable
data class TabLayoutPresenter<T>(
    val tabs: StoreObject<List<T>>,
    val pagerLayoutState: PagerLayoutState,
    val getTabLabel: @Composable (tab: T) -> String,
    val selectedTab: StoreObject<T> = StoreObject()
) {
    fun onSelectTab(tab: T) {
        pagerLayoutState.onPage(tabs.getNotNull().indexOf(tab))
        selectedTab.set(tab)
    }

    companion object {
        @Composable
        fun <T> default(
            tabs: StoreObject<List<T>>,
            getTabLabel: @Composable (tab: T) -> String,
            selectedTab: StoreObject<T> = StoreObject()
        ): TabLayoutPresenter<T> {
            val scope = rememberCoroutineScope()
            val pages = tabs.asStateValueNotNull()
            val index = selectedTab.get()?.let(pages::indexOf) ?: 0
            val pagerState = rememberPagerLayoutState(pages, false, index)
            LaunchedEffect(pages) {
                val newIndex = selectedTab.get()?.let(pages::indexOf) ?: 0
                scope.launch { pagerState.onPage(newIndex) }
            }
            return remember(pages) {
                TabLayoutPresenter(
                    tabs = tabs,
                    getTabLabel = getTabLabel,
                    selectedTab = selectedTab,
                    pagerLayoutState = pagerState
                )
            }
        }
    }
}

@Composable
fun <T> TabLayout(
    modifier: Modifier = Modifier,
    presenter: TabLayoutPresenter<T>,
    appearance: TabLayoutAppearance = TabLayoutAppearance.default(),
    tabLayout: @Composable (tab: T, selected: Boolean) -> Unit = { tab, selected ->
        TabBlock(
            horizontalPadding = 16.dp,
            text = presenter.getTabLabel.invoke(tab),
            selected = selected,
            onClick = { presenter.onSelectTab(tab) }
        )
    },
    pageLayout: @Composable (tab: T?) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        SpacerStatusBar()
        Spacer8()
        TabsBlock(
            appearance = appearance,
            presenter = presenter,
            tabLayout = tabLayout
        )
        DividerBlock()
        Spacer4()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.color.surfacePrimary)
        ) {
            ContentBlock(
                appearance = appearance,
                presenter = presenter,
                pageLayout = pageLayout
            )
        }
    }
}

@Composable
private fun <T> TabsBlock(
    appearance: TabLayoutAppearance,
    presenter: TabLayoutPresenter<T>,
    tabLayout: @Composable (tab: T, selected: Boolean) -> Unit
) {
    ScrollableRowLayout(
        modifier = Modifier.fillMaxWidth(),
        itemsState = presenter.tabs.asStateNotNull(),
        selectedItem = presenter.selectedTab.asState(),
        appearance = ScrollableRowLayoutAppearance.default(
            withIndicators = appearance.withTabIndicators,
            withDivider = false
        )
    ) { item, selected -> tabLayout(item, selected) }
}

@Composable
private fun <T> ContentBlock(
    appearance: TabLayoutAppearance,
    presenter: TabLayoutPresenter<T>,
    pageLayout: @Composable (tab: T?) -> Unit
) {
    HorizontalPagerLayout(
        withBackHandler = appearance.withBackHandler,
        state = presenter.pagerLayoutState,
        pages = presenter.tabs.asStateValueNotNull(),
        onPageChanged = {
            presenter.tabs.getNotNull().getOrNull(it)?.let(presenter.selectedTab::set)
        }
    ) { _, tab ->
        val selectedTab = presenter.selectedTab
        val visible = tab == selectedTab.asStateValue() || tab == selectedTab.getPrev()
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            if (visible) {
                pageLayout(tab)
            }
        }
    }
}