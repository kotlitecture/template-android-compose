package core.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.data.state.StoreObject
import core.ui.app.theme.AppTheme
import core.ui.block.IconBlock
import core.ui.block.MediumTitlePrimary
import core.ui.block.SmallTextSecondary
import core.ui.block.Spacer12
import core.ui.block.Spacer16
import core.ui.block.SquaredIconBlock
import core.ui.misc.extension.withClickable

@Immutable
data class ActionsSheetState(
    val actions: List<Action>,
    val uid: Long = System.currentTimeMillis()
) {

    @Immutable
    data class Action(
        val iconStart: Any,
        val getColor: @Composable () -> Color,
        val getTitle: @Composable () -> String,
        val getDescription: @Composable () -> String? = { null },
        val ignoreIconTint: Boolean = false,
        val iconEndSize: Dp = Dp.Unspecified,
        val iconEnd: Any? = null,
        val onClick: () -> Unit
    )
}

@Composable
fun ActionsSheetLayout(
    actionsStore: StoreObject<ActionsSheetState>,
    closeStore: StoreObject<*> = actionsStore,
    header: @Composable () -> Unit = {}
) {
    if (closeStore.asStateValue() == null) return
    val state = actionsStore.asStateValue() ?: return
    BottomSheetLayout(onDismissRequest = closeStore::clear) {
        header()
        state.actions.forEach { action ->
            ActionItem(closeStore = closeStore, action = action)
        }
    }
}

@Composable
private fun ActionItem(
    closeStore: StoreObject<*>,
    action: ActionsSheetState.Action
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .withClickable {
                closeStore.clear()
                action.onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer16()
        SquaredIconBlock(
            modifier = Modifier.padding(vertical = 16.dp),
            ignoreColor = action.ignoreIconTint,
            size = AppTheme.size.iconXl,
            model = action.iconStart,
            tint = action.getColor(),
        )
        Spacer12()
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            MediumTitlePrimary(
                text = action.getTitle(),
                maxLines = 1
            )
            action.getDescription()?.let {
                SmallTextSecondary(
                    text = it,
                    maxLines = 3
                )
            }
        }
        if (action.iconEnd != null) {
            IconBlock(
                model = action.iconEnd,
                size = action.iconEndSize,
                tint = AppTheme.color.textSecondary
            )
        }
        Spacer16()
    }
}