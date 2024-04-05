package app.ui.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import app.ui.component.basic.SpacerDynamic
import app.ui.component.basic.SpacerNavigationBar
import app.ui.component.basic.SpacerStatusBar
import core.ui.theme.ThemeData

/**
 * Represents the appearance configuration for a layout.
 *
 * @property backgroundColor The background color of the fixed header layout.
 * @property headerBrush The brush used to paint the header section of the fixed header layout.
 * @property footerBrush The brush used to paint the footer section of the fixed header layout.
 * @property statusSpacer Whether to include a spacer for the system status bar.
 * @property navigationSpacer Whether to include a spacer for the system navigation bar.
 */
data class FixedHeaderFooterAppearance(
    val backgroundColor: Color,
    val headerBrush: Brush,
    val footerBrush: Brush,
    val statusSpacer: Boolean,
    val navigationSpacer: Boolean
) {
    companion object {
        @Stable
        @Composable
        @ReadOnlyComposable
        fun default(
            backgroundColor: Color = ThemeData.current.primary,
            headerBrush: Brush? = ThemeData.current.topBlur,
            footerBrush: Brush? = ThemeData.current.bottomBlur,
            statusSpacer: Boolean = true,
            navigationSpacer: Boolean = true
        ): FixedHeaderFooterAppearance {
            return FixedHeaderFooterAppearance(
                backgroundColor = backgroundColor,
                headerBrush = headerBrush ?: SolidColor(Color.Unspecified),
                footerBrush = footerBrush ?: SolidColor(Color.Unspecified),
                statusSpacer = statusSpacer,
                navigationSpacer = navigationSpacer
            )
        }
    }
}

/**
 * Composable function representing a Column layout with fixed header and footer.
 *
 * @param modifier The modifier to apply to the layout.
 * @param appearance The appearance configuration for the fixed header layout.
 * @param header The composable content for the header section.
 * @param footer The composable content for the footer section.
 * @param content The main content of the fixed header layout.
 */
@Composable
fun FixedHeaderFooterColumnLayout(
    modifier: Modifier = Modifier,
    appearance: FixedHeaderFooterAppearance = FixedHeaderFooterAppearance.default(),
    header: @Composable (ColumnScope.() -> Unit)? = null,
    footer: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val headerHeight = remember { mutableStateOf(-1) }
    val footerHeight = remember { mutableStateOf(-1) }
    Box(modifier = modifier.fillMaxSize()) {
        ContentBlock(appearance, headerHeight, footerHeight, content)
        HeaderBlock(appearance, headerHeight, header)
        FooterBlock(appearance, footerHeight, footer)
    }
}

/**
 * Composable function representing a LazyColumn layout with fixed header and footer.
 *
 * @param modifier The modifier to apply to the layout.
 * @param appearance The appearance configuration for the fixed header layout.
 * @param header The composable content for the header section.
 * @param footer The composable content for the footer section.
 * @param content The main content of the fixed header layout.
 */
@Composable
fun FixedHeaderFooterLazyColumnLayout(
    modifier: Modifier = Modifier,
    appearance: FixedHeaderFooterAppearance = FixedHeaderFooterAppearance.default(),
    header: @Composable (ColumnScope.() -> Unit)? = null,
    footer: @Composable (ColumnScope.() -> Unit)? = null,
    content: LazyListScope.() -> Unit
) {
    val headerHeight = remember { mutableStateOf(-1) }
    val footerHeight = remember { mutableStateOf(-1) }
    Box(modifier = modifier.fillMaxSize()) {
        ContentBlock(appearance, headerHeight, footerHeight, content)
        HeaderBlock(appearance, headerHeight, header)
        FooterBlock(appearance, footerHeight, footer)
    }
}

@Composable
private fun BoxScope.HeaderBlock(
    appearance: FixedHeaderFooterAppearance,
    state: MutableState<Int>,
    content: @Composable (ColumnScope.() -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
            .background(appearance.headerBrush)
            .onSizeChanged { state.value = it.height }
    ) {
        if (appearance.statusSpacer) {
            SpacerStatusBar()
        }
        content?.invoke(this)
    }
}

@Composable
private fun BoxScope.FooterBlock(
    appearance: FixedHeaderFooterAppearance,
    state: MutableState<Int>,
    content: @Composable (ColumnScope.() -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(appearance.footerBrush)
            .onSizeChanged { state.value = it.height }
    ) {
        content?.invoke(this)
        if (appearance.navigationSpacer) {
            SpacerNavigationBar()
        }
    }
}

@Composable
private fun ContentBlock(
    appearance: FixedHeaderFooterAppearance,
    headerState: State<Int>,
    footerState: State<Int>,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appearance.backgroundColor)
            .verticalScroll(rememberScrollState())
    ) {
        if (headerState.value >= 0 && footerState.value >= 0) {
            SpacerDynamic(heightState = headerState)
            content.invoke(this)
            SpacerDynamic(heightState = footerState)
        }
    }
}

@Composable
private fun ContentBlock(
    appearance: FixedHeaderFooterAppearance,
    headerState: State<Int>,
    footerState: State<Int>,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(appearance.backgroundColor)
    ) {
        if (headerState.value >= 0 && footerState.value >= 0) {
            item { SpacerDynamic(heightState = headerState) }
            content.invoke(this)
            item { SpacerDynamic(heightState = footerState) }
        }
    }
}