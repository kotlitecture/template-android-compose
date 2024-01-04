package core.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.ui.block.ButtonAppearance
import core.ui.block.ButtonBlock
import core.ui.block.withPlaceholder
import core.ui.misc.extension.withPaddingHorizontal16

@Composable
@NonRestartableComposable
fun ActionsRowLayout(
    placeholder:Boolean,
    primaryText: String,
    secondaryText: String,
    onPrimary: () -> Unit,
    onSecondary: () -> Unit,
    onMore: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .withPaddingHorizontal16(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ButtonBlock(
            modifier = Modifier
                .weight(1f)
                .withPlaceholder(placeholder)
            ,
            appearance = ButtonAppearance.primarySm(),
            enabled = !placeholder,
            text = primaryText,
            onClick = onPrimary
        )
        ButtonBlock(
            modifier = Modifier
                .weight(1f)
                .withPlaceholder(placeholder)
            ,
            appearance = ButtonAppearance.secondarySm(),
            enabled = !placeholder,
            text = secondaryText,
            onClick = onSecondary
        )
        ButtonBlock(
            modifier = Modifier
                .size(40.dp)
                .withPlaceholder(placeholder)
            ,
            appearance = ButtonAppearance.secondarySm(),
            enabled = !placeholder,
            icon = Icons.Default.MoreHoriz,
            onClick = onMore
        )
    }
}

@Composable
@NonRestartableComposable
fun ActionsRowLayout(
    primaryAppearance:ButtonAppearance = ButtonAppearance.primaryMd(),
    secondaryAppearance:ButtonAppearance = ButtonAppearance.secondaryMd(),
    primaryText: String,
    secondaryText: String,
    onPrimary: () -> Unit,
    onSecondary: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .withPaddingHorizontal16(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ButtonBlock(
            modifier = Modifier.weight(1f),
            appearance = secondaryAppearance,
            text = secondaryText,
            onClick = onSecondary
        )
        ButtonBlock(
            modifier = Modifier.weight(1f),
            appearance = primaryAppearance,
            text = primaryText,
            onClick = onPrimary
        )
    }
}