package app.userflow.passcode.ui.reset

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.R
import app.appViewModel

@Preview
@Composable
fun ResetPasscodeScreen() {
    val viewModel: ResetPasscodeViewModel = appViewModel()
    AlertDialog(
        onDismissRequest = viewModel::onBack,
        title = {
            Text(text = stringResource(R.string.passcode_reset_title))
        },
        text = {
            Text(text = stringResource(R.string.passcode_reset_text))
        },
        confirmButton = {
            TextButton(onClick = viewModel::onReset) {
                Text(text = stringResource(R.string.passcode_reset_yes))
            }
        },
        dismissButton = {
            TextButton(onClick = viewModel::onBack) {
                Text(text = stringResource(R.string.passcode_reset_no))
            }
        }
    )
}