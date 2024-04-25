package app.ui.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import app.R

fun <I : Any> LazyListScope.SimpleLazyPagingList(
    items: LazyPagingItems<I>?,
    itemKey: ((index: Int) -> Any)? = null,
    itemContent: @Composable (item: I?) -> Unit,
    emptyContent: @Composable () -> Unit = {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "(^-^*)", fontSize = 24.sp, fontWeight = FontWeight.W600)
            Text(text = stringResource(R.string.data_not_found))
        }
    },
    refreshContent: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    },
    appendContent: @Composable () -> Unit = refreshContent
) {
    val refreshState = items?.loadState?.refresh
    when {
        items == null -> {
            item { refreshContent() }
        }

        items.itemCount == 0 && refreshState is LoadState.Loading -> {
            item { refreshContent() }
        }

        items.itemCount == 0 && refreshState is LoadState.NotLoading -> {
            item { emptyContent() }
        }

        else -> {
            items(items.itemCount, key = itemKey) { index ->
                itemContent(items[index])
            }
            if (items.loadState.append is LoadState.Loading) {
                item { appendContent() }
            }
        }
    }
}