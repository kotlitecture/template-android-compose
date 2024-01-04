package core.ui.block

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme
import core.ui.misc.extension.withClickable
import core.ui.misc.extension.withCornerRadius

@Composable
@NonRestartableComposable
fun TabBlock(
    placeholder: Boolean = false,
    text: String,
    selected: Boolean,
    horizontalPadding: Dp = 12.dp,
    onClick: () -> Unit
) {
    val corner = AppTheme.size.cornerSm
    val color = AppTheme.color.getTextVariant(selected)
    Row(
        modifier = Modifier
            .padding(end = 8.dp)
            .withCornerRadius(corner)
            .withClickable(enabled = !placeholder, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MediumTitlePrimary(
            modifier = Modifier
                .padding(horizontal = horizontalPadding, vertical = 12.dp)
                .withPlaceholder(placeholder),
            color = color,
            text = text
        )
    }
}