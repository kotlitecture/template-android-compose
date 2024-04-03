package app.ui.container

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.ui.component.basic.ActionButton
import core.ui.theme.ThemeData

/**
 * Composable function for rendering a layout with a fixed top bar.
 *
 * @param title The title text to display in the top app bar.
 * @param onBack The callback to be invoked when the back navigation icon is clicked.
 * @param content The content to be displayed below the top bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedTopBarLayout(
    title: String? = null,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    FixedHeaderFooterLayout(
        modifier = Modifier.fillMaxSize(),
        appearance = FixedHeaderFooterAppearance.default(),
        header = {
            TopAppBar(
                windowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = ThemeData.current.onPrimary,
                    actionIconContentColor = ThemeData.current.onPrimary,
                    navigationIconContentColor = ThemeData.current.onPrimary,
                ),
                title = {
                    if (title != null) {
                        Text(text = title)
                    }
                },
                actions = actions,
                navigationIcon = {
                    if (onBack != null) {
                        ActionButton(
                            onClick = onBack,
                            icon = Icons.Default.ArrowBackIosNew
                        )
                    }
                }
            )
        },
        content = content
    )
}