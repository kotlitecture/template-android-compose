package core.ui.layout.item

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun <T : Any> LazyListScope.pagingItems(
    items: LazyPagingItems<T>?,
    emptyState: LazyListScope.() -> Unit = {},
    loadingState: LazyListScope.() -> Unit = {},
    itemKey: (item: T?) -> Any? = { it?.hashCode() },
    itemType: (item: T?) -> Any? = { it?.javaClass?.simpleName },
    itemContent: @Composable LazyItemScope.(item: T?) -> Unit
) {
    val refreshState = items?.loadState?.refresh
    val itemsCount = items?.itemCount
    when {
        itemsCount == null -> loadingState()
        itemsCount == 0 && refreshState is LoadState.NotLoading -> emptyState()
        itemsCount == 0 -> loadingState()
        else -> {
            items(
                count = itemsCount,
                contentType = { itemType(items.peek(it)) },
                key = { itemKey(items.peek(it)) ?: it },
                itemContent = { itemContent(items[it]) }
            )
        }
    }
}