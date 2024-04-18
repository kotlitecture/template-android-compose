package app.userflow.passcode.ui.enable.set

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.R
import app.provideHiltViewModel
import app.ui.container.FixedTopBarColumnLayout
import app.userflow.passcode.ui.common.PasscodeLayout

@Composable
fun SetPasscodeScreen() {
    val viewModel: SetPasscodeViewModel = provideHiltViewModel()
    FixedTopBarColumnLayout(
        onBack = viewModel::onBack
    ) {
        PasscodeLayout(
            title = stringResource(R.string.passcode_enable_set),
            codeState = viewModel.enteredCodeStore.asState(),
            codeLength = viewModel.passcodeLength,
            onCodeChange = viewModel::onCodeChanged
        )
    }
}