package core.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import core.ui.app.theme.AppTheme
import core.ui.block.SpacerNavigationBar
import core.ui.misc.extension.traceRecompose
import core.ui.misc.extension.vibrateOk

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeRefreshLayout(
    modifier: Modifier = Modifier,
    indicatorModifier: Modifier = Modifier,
    navigationColor: Color = AppTheme.color.statusPrimary,
    onRefresh: (() -> Unit)?,
    content: @Composable BoxScope.() -> Unit
) {
    traceRecompose("SwipeRefreshLayout", navigationColor)
    if (onRefresh != null) {
        val context = LocalContext.current
        val pullRefreshState = rememberPullRefreshState(
            refreshing = false,
            onRefresh = {
                onRefresh()
                context.vibrateOk()
            })
        Box(
            modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            content()
            PullRefreshIndicator(
                scale = true,
                refreshing = false,
                modifier = indicatorModifier.align(Alignment.TopCenter),
                state = pullRefreshState
            )
            SpacerNavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(navigationColor)
            )
        }
    } else {
        Box(modifier.fillMaxSize()) {
            content()
            SpacerNavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(navigationColor)
            )
        }
    }
}