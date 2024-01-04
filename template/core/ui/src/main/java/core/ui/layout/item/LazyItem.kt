package core.ui.layout.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.lazyItem(
    key: Any? = null,
    sticky: Boolean = false,
    contentType: Any? = null,
    content: @Composable () -> Unit
) {
    val type = contentType?.let { ContentType(contentType, sticky) }
    if (sticky) {
        stickyHeader(contentType = type, key = key) {
            content()
        }
    } else {
        item(contentType = type, key = key) {
            content()
        }
    }
}

internal data class ContentType(
    val contentType: Any,
    val sticky: Boolean
)