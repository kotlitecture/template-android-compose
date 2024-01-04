package core.ui.app.dialog.confirm

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import core.data.state.StoreObject
import core.ui.R
import core.ui.block.ButtonAppearance
import core.ui.block.ButtonBlock
import core.ui.block.MediumTitlePrimary
import core.ui.block.SmallHeaderPrimary
import core.ui.block.SmallTextSecondary
import core.ui.block.Spacer12
import core.ui.block.Spacer24
import core.ui.block.Spacer32
import core.ui.block.Spacer8
import core.ui.layout.BottomSheetLayout
import core.ui.misc.extension.withPaddingHorizontal16

data class ConfirmState(
    val title: String,
    val message: String,
    val confirmLabel: String,
    val onConfirm: (store: StoreObject<ConfirmState>) -> Unit
) {
    private val uid: Long = System.currentTimeMillis()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConfirmState) return false

        if (uid != other.uid) return false

        return true
    }

    override fun hashCode(): Int {
        return uid.hashCode()
    }

}

@Composable
fun ConfirmOverlay(store: StoreObject<ConfirmState>) {
    val state = store.asStateValue() ?: return
    BottomSheetLayout(onDismissRequest = store::clear) {
        Spacer8()
        SmallHeaderPrimary(
            modifier = Modifier.withPaddingHorizontal16(),
            maxLines = Int.MAX_VALUE,
            text = state.title
        )
        Spacer12()
        MediumTitlePrimary(
            modifier = Modifier.withPaddingHorizontal16(),
            maxLines = Int.MAX_VALUE,
            text = state.message
        )
        Spacer32()
        ButtonBlock(
            modifier = Modifier
                .fillMaxWidth()
                .withPaddingHorizontal16(),
            appearance = ButtonAppearance.primaryMd(),
            text = state.confirmLabel,
            onClick = { state.onConfirm(store) }
        )
        Spacer24()
        SmallTextSecondary(
            modifier = Modifier
                .fillMaxWidth()
                .withPaddingHorizontal16(),
            maxLines = 1,
            textAlign = TextAlign.Center,
            text = stringResource(R.string.action_no_undone)
        )
        Spacer24()
        ButtonBlock(
            modifier = Modifier
                .fillMaxWidth()
                .withPaddingHorizontal16(),
            appearance = ButtonAppearance.secondaryMd(),
            text = stringResource(R.string.button_cancel),
            onClick = store::clear
        )
    }

}