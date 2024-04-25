package app.userflow.passcode.ui.enable.confirm

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.R
import app.appViewModel
import app.ui.container.FixedTopBarColumnLayout
import app.userflow.passcode.ui.common.PasscodeLayout

@Composable
fun ConfirmPasscodeScreen(data: ConfirmPasscodeDestination.Data) {
    val viewModel: ConfirmPasscodeViewModel = appViewModel()
    FixedTopBarColumnLayout(
        onBack = viewModel::onBack
    ) {
        PasscodeLayout(
            codeLength = data.code.length,
            title = stringResource(R.string.passcode_enable_confirm),
            codeState = viewModel.enteredCodeStore.asState(),
            errorState = viewModel.errorStore.asState(),
            onCodeChange = { viewModel.onCodeChanged(data.code, it) }
        )
    }
}