package core.ui.layout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import core.ui.block.DividerBlock
import kotlinx.coroutines.launch

@Composable
fun ScrollToTopHandler(state: LazyListState, items: LazyPagingItems<out Any>? = null) {
    val scope = rememberCoroutineScope()
    if (items?.loadState != null && !state.canScrollBackward) {
        SideEffect {
            scope.launch {
                state.animateScrollToItem(0)
            }
        }
    }
}

@Composable
fun DividerHandler(state: LazyListState) {
    if (state.canScrollBackward) {
        DividerBlock()
    }
}

@Composable
fun LazyColumnLayout(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier.fillMaxSize(),
        state = state
    ) {
        content()
    }
}

@Stable
fun Modifier.withScrollHandler(
    itemKey: String,
    state: LazyListState,
    handler: Modifier.(info: LazyListItemInfo) -> Modifier
): Modifier {
    val info = state.layoutInfo.visibleItemsInfo.find { it.key == itemKey } ?: return this
    return handler(this, info)
}