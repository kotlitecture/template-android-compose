package core.ui.layout.item

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import core.ui.block.DividerBlock

fun LazyListScope.dividerItem(modifier: Modifier = Modifier) {
    lazyItem(contentType = "dividerItem") {
        DividerBlock(modifier)
    }
}