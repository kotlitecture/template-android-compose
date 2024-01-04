package core.ui.layout.item

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme
import core.ui.block.SpacerFullHeight
import core.ui.block.SpacerNavigationBar
import core.ui.block.SpacerStatusBar

fun LazyListScope.spacerItem(modifier: Modifier = Modifier, height: Dp) {
    lazyItem(contentType = "spacerItem") {
        Spacer(modifier = modifier.size(height))
    }
}

fun LazyListScope.spacerItem4(modifier: Modifier = Modifier) {
    spacerItem(modifier, 4.dp)
}

fun LazyListScope.spacerItem8(modifier: Modifier = Modifier) {
    spacerItem(modifier, 8.dp)
}

fun LazyListScope.spacerItem12(modifier: Modifier = Modifier) {
    spacerItem(modifier, 12.dp)
}

fun LazyListScope.spacerItem16(modifier: Modifier = Modifier) {
    spacerItem(modifier, 16.dp)
}

fun LazyListScope.spacerItem24(modifier: Modifier = Modifier) {
    spacerItem(modifier, 24.dp)
}

fun LazyListScope.spacerItem32(modifier: Modifier = Modifier) {
    spacerItem(modifier, 32.dp)
}

fun LazyListScope.spacerItem40(modifier: Modifier = Modifier) {
    spacerItem(modifier, 40.dp)
}

fun LazyListScope.spacerItem56(modifier: Modifier = Modifier) {
    spacerItem(modifier, 56.dp)
}

fun LazyListScope.spacerToolbar(modifier: Modifier = Modifier) {
    lazyItem(contentType = "spacerItem") {
        Spacer(modifier = modifier.height(AppTheme.size.toolbarHeight))
    }
}

fun LazyListScope.spacerStatus(modifier: Modifier = Modifier, sticky: Boolean = false) {
    lazyItem(contentType = "SpacerStatusBar", sticky = sticky) {
        SpacerStatusBar(
            modifier = modifier
                .fillMaxWidth()
        )
    }
}

fun LazyListScope.spacerNavigationBar(modifier: Modifier = Modifier) {
    lazyItem(contentType = "SpacerNavigationBar") {
        Spacer(modifier = modifier.height(AppTheme.size.navigationBarSize))
    }
}

fun LazyListScope.spacerNavigation(modifier: Modifier = Modifier) {
    lazyItem(contentType = "SpacerNavigation") {
        SpacerNavigationBar(
            modifier = modifier
                .fillMaxWidth()
        )
    }
}

fun LazyListScope.spacerFullHeight(modifier: Modifier = Modifier, minus: Dp = 0.dp) {
    lazyItem(contentType = "SpacerFullHeight", sticky = false) {
        SpacerFullHeight(modifier = modifier.fillMaxWidth(), minus = minus)
    }
}