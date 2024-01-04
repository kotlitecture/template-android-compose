package core.ui.layout

import androidx.compose.animation.core.animate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Velocity
import core.ui.misc.extension.traceRecompose
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

@Immutable
data class CoordinatorState(
    val scrollState: MutableState<LazyListState?> = mutableStateOf(null),
    val fullscreenState: MutableState<Boolean> = mutableStateOf(false),
    val progressState: MutableState<Float> = mutableStateOf(0f),
) {
    companion object {
        @Stable
        @Composable
        fun default(): CoordinatorState {
            val fullscreenState = rememberSaveable { mutableStateOf(false) }
            val progressState = rememberSaveable { mutableStateOf(0f) }
            return remember {
                CoordinatorState(
                    fullscreenState = fullscreenState,
                    progressState = progressState
                )
            }
        }
    }
}

@Composable
fun <T> bind(
    coordinatorState: CoordinatorState,
    tabState: TabLayoutPresenter<T>,
    listState: LazyListState,
    tab: T?
) {
    if (tabState.selectedTab.asStateValue() == tab) {
        SideEffect {
            coordinatorState.scrollState.value = listState
        }
    }
}

@Composable
fun bind(
    coordinatorState: CoordinatorState,
    listState: LazyListState
) {
    SideEffect {
        coordinatorState.scrollState.value = listState
    }
}

@Composable
fun CoordinatorLayout(
    modifier: Modifier = Modifier,
    collapsableHeader: @Composable () -> Unit,
    state: CoordinatorState = CoordinatorState.default(),
    content: @Composable () -> Unit
) {
    traceRecompose("CoordinatorLayout", state)
    val headerHeightState = rememberSaveable { mutableStateOf(0f) }
    val scrollScope = rememberCoroutineScope()
    Column(modifier = modifier
        .fillMaxSize()
        .nestedScroll(
            remember {
                object : NestedScrollConnection {

                    var postScrollTo: Float = -1f
                    var postScrollJob: Job? = null

                    val scrollState = state.scrollState
                    val progressState = state.progressState
                    val fullscreenState = state.fullscreenState

                    override suspend fun onPreFling(available: Velocity): Velocity {
                        if (postScrollTo == -1f) {
                            return Velocity.Zero
                        }
                        if (postScrollTo == 1f && progressState.value < postScrollTo) {
                            return available
                        }
                        return Velocity.Zero
                    }

                    override suspend fun onPostFling(
                        consumed: Velocity,
                        available: Velocity
                    ): Velocity {
                        val scrollDelta = 0.15f
                        var scrollTo = postScrollTo
                        val scrollFrom = progressState.value
                        if (scrollTo != -1f && scrollFrom > 0f && scrollFrom < 1f) {
                            postScrollJob?.cancel()
                            when {
                                scrollTo == 1f && scrollFrom < scrollDelta -> {
                                    scrollTo = 0f
                                }

                                scrollTo == 0f && (1f - scrollFrom) < scrollDelta -> {
                                    scrollTo = 1f
                                }
                            }
                            postScrollJob = scrollScope.launch {
                                animate(scrollFrom, scrollTo) { value, _ ->
                                    progressState.value = value
                                }
                                fullscreenState.value = scrollTo == 1f
                            }
                        }
                        return Velocity.Zero
                    }

                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        postScrollJob?.cancel()
                        postScrollJob = null
                        val scrolledX = available.x
                        val scrolledY = available.y
                        if (scrolledX.absoluteValue > scrolledY.absoluteValue) {
                            postScrollTo = -1f
                            return available.copy(x = 0f)
                        }
                        val progress = progressState.value
                        val headerHeight = headerHeightState.value
                        val scrolledProgress = scrolledY / headerHeight
                        if (scrolledY < 0f) {
                            postScrollTo = 1f
                        } else if (scrolledY > 0f) {
                            postScrollTo = 0f
                        }
                        val listState = scrollState.value
                        if (progress == 1f && scrolledY > 0f && listState != null && listState.canScrollBackward) {
                            return available.copy(y = 0f)
                        }
                        val newProgress = min(1f, max(0f, progress - scrolledProgress))
                        progressState.value = newProgress
                        if (newProgress == 1f) {
                            fullscreenState.value = true
                        } else if (newProgress == 0f) {
                            fullscreenState.value = false
                        }
                        if (scrolledY < 0f && newProgress < 1f) {
                            return available
                        }
                        if (scrolledY > 0f && newProgress > 0f) {
                            return available
                        }
                        return available.copy(y = 0f)
                    }
                }
            }
        )
    ) {
        Box(modifier = Modifier
            .onSizeChanged { size ->
                if (size.height > headerHeightState.value) {
                    val height = size.height.toFloat()
                    headerHeightState.value = height
                }
            }
            .verticalScroll(rememberScrollState())
        ) {
            collapsableHeader()
        }
        content()
    }
}