package core.ui.app.dialog.error

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import core.ui.R
import core.ui.app.dialog.DialogButton
import core.ui.app.dialog.DialogLayout
import core.ui.app.theme.AppTheme
import core.ui.mvvm.provideViewModel

@Composable
fun ErrorDialogScreen(data:ErrorDialogDestination.Data) {
    val viewModel: ErrorDialogViewModel = provideViewModel()
    ErrorDialogLayout(
        title = data.title ?: stringResource(R.string.error_unknown),
        message = data.message,
        closeLabel = data.actionLabel,
        onClose = viewModel::onBack
    )
}

@Composable
private fun ErrorDialogLayout(
    title: String? = null,
    message: String,
    closeLabel: String? = null,
    onClose: () -> Unit = {}
) {
    DialogLayout(
        title = title,
        message = message
    ) {
        DialogButton(
            text = closeLabel ?: stringResource(R.string.button_ok),
            onClick = onClose
        )
    }
}

@Preview
@Composable
private fun PreviewErrorDialogLayout() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            ErrorDialogLayout(
                message = "Incorrect email or password. (Please note that these are case sensitive.) You have 4 attempts left.. ",
                closeLabel = "Continue"
            )
        }
    }
}