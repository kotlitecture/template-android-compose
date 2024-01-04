package core.ui.app.dialog.hint

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.essentials.misc.extensions.trimLines
import core.ui.R
import core.ui.app.dialog.DialogButton
import core.ui.app.dialog.DialogLayout
import core.ui.app.theme.AppTheme
import core.ui.block.MediumTitlePrimary
import core.ui.mvvm.provideViewModel

@Composable
fun HintDialogScreen(data: HintDialogDestination.Data?) {
    val viewModel: HintDialogViewModel = provideViewModel()
    if (data == null) {
        LaunchedEffect(viewModel) {
            viewModel.onBack()
        }
    } else {
        HintDialogLayout(
            title = data.title?.trimLines(),
            message = data.message.trimLines(),
            icon = data.icon,
            actionLabel = data.actionLabel,
            onClose = viewModel::onBack
        )
    }
}

@Composable
private fun HintDialogLayout(
    title: String? = null,
    message: String,
    actionLabel: String? = null,
    icon: Any? = null,
    onClose: () -> Unit = {}
) {
    DialogLayout(
        icon = icon,
        title = title,
        message = message,
    ) {
        DialogButton(
            text = actionLabel ?: stringResource(R.string.button_ok),
            onClick = onClose
        )
    }
}

@Preview
@Composable
private fun PreviewHintDialogLayout() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HintDialogLayout(
                title = "Title",
                message = "Message",
                actionLabel = "Got it!",
                icon = Icons.Default.AccountBalance
            )

            MediumTitlePrimary(
                text = "Title",
                iconSize = 12.dp,
                iconEnd = Icons.Default.Info,
                iconColor = AppTheme.color.textHint
            )
        }
    }
}