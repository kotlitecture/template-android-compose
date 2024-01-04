package core.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme
import core.ui.block.DividerBlock
import core.ui.block.SpacerNavigationBar
import core.ui.block.TopBarBlock
import core.ui.layout.item.spacerItem
import core.ui.layout.item.spacerNavigation
import core.ui.misc.DisplayUnit

@Composable
fun FixedTopBarLayout(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBack: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Box {
        val topBarHeight = remember { mutableFloatStateOf(0f) }
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement
        ) {
            Spacer(modifier = Modifier.height(topBarHeight.value.dp))
            content.invoke(this)
            SpacerNavigationBar()
        }
        TopBarBlock(title = title,
            onBack = onBack,
            onClose = onClose,
            actions = actions,
            backgroundColor = AppTheme.color.statusPrimary,
            modifier = Modifier.onSizeChanged {
                topBarHeight.value = DisplayUnit.PX.toDp(it.height)
            })
    }
}

@Composable
fun FixedTopBarLazyLayout(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBack: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    actions: @Composable RowScope.() -> Unit = {},
    footer: (@Composable ColumnScope.() -> Unit)? = null,
    content: LazyListScope.() -> Unit
) {
    Box(modifier = modifier) {
        val headerHeight = remember { mutableFloatStateOf(0f) }
        val footerHeight = remember { mutableFloatStateOf(0f) }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = horizontalAlignment
        ) {
            spacerItem(height = headerHeight.value.dp)
            content.invoke(this)
            spacerItem(height = footerHeight.value.dp)
            if (footer == null) {
                spacerNavigation()
            }
        }
        TopBarBlock(title = title,
            onBack = onBack,
            onClose = onClose,
            actions = actions,
            backgroundColor = AppTheme.color.statusPrimary,
            modifier = Modifier.onSizeChanged {
                headerHeight.value = DisplayUnit.PX.toDp(it.height)
            })
        if (footer != null) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
//                .withAdvancedShadow(
//                    color = AppTheme.color.shadowPrimary,
//                    borderRadius = AppTheme.size.cornerMd,
//                    blurRadius = 4.dp,
//                    spread = 2f,
//                    withBottomShadow = false,
//                    withTopShadow = true,
//                )
//                .withTopCornerRadius(AppTheme.size.cornerMd)
                .background(AppTheme.color.surfacePrimary)
                .onSizeChanged {
                    footerHeight.value = DisplayUnit.PX.toDp(it.height)
                }) {
                DividerBlock()
                footer()
                SpacerNavigationBar()
            }
        }
    }
}