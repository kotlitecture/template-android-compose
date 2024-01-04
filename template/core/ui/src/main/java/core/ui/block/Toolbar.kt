package core.ui.block

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.ui.app.theme.AppTheme

@Composable
@NonRestartableComposable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    titleColor: Color? = null,
    onBack: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    navigationContent: @Composable RowScope.() -> Unit = {
        if (onBack != null) {
            ActionButtonBlock(
                icon = Icons.Default.ArrowBackIosNew,
                onClick = onBack
            )
        }
    },
    titleContent: @Composable RowScope.() -> Unit = {
        if (title != null) {
            MediumTitlePrimary(
                modifier = Modifier.weight(1f),
                color = titleColor ?: AppTheme.color.textPrimary,
                text = title
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    },
    actions: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.size.toolbarHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer8()
        navigationContent()
        Spacer8()
        titleContent()
        Spacer8()
        actions()
        Spacer8()
        if (onClose != null) {
            ActionButtonBlock(
                modifier = Modifier,
                icon = Icons.Filled.Close,
                onClick = onClose
            )
            Spacer8()
        }
    }
}