package app.userflow.passcode.ui.unlock

import androidx.activity.compose.BackHandler
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import app.R
import app.provideHiltViewModel
import app.ui.component.basic.Spacer16
import app.ui.container.FixedTopBarColumnLayout
import app.userflow.passcode.ui.common.PadIconButton
import app.userflow.passcode.ui.common.PadTextButton
import app.userflow.passcode.ui.common.PasscodeLayout
import core.ui.misc.extensions.findActivity
import core.ui.theme.ThemeData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun UnlockPasscodeScreen(data: UnlockPasscodeDestination.Data) {
    val viewModel: UnlockPasscodeViewModel = provideHiltViewModel()
    LaunchedEffect(data) { viewModel.onBind(data) }
    FixedTopBarColumnLayout(
        title = data.title,
        onBack = viewModel::onBack.takeIf { data.soft }
    ) {
        PasscodeLayout(
            title = stringResource(R.string.passcode_unlock),
            errorState = viewModel.errorStore.asState(),
            codeState = viewModel.enteredCodeStore.asState(),
            codeLength = viewModel.passcodeLength,
            onCodeChange = viewModel::onCodeChanged,
            bottomLeftBlock = {
                if (viewModel.canForgetPPasscode) {
                    PadTextButton(
                        text = stringResource(R.string.passcode_forgot),
                        onClick = viewModel::onForgot
                    )
                } else {
                    Box(modifier = Modifier.size(it))
                }
            }
        )
        BiometricButtonBlock(viewModel)
    }
    BiometricListener(viewModel)
    if (!data.soft) {
        BackHandler {}
    }
}

@Composable
private fun BiometricListener(viewModel: UnlockPasscodeViewModel) {
    val activity = LocalContext.current.findActivity() ?: return
    LaunchedEffect(activity, viewModel) {
        viewModel.biometricAvailable.asFlow()
            .filterNotNull()
            .filter { it }
            .collectLatest {
                val prompt = BiometricPrompt(
                    activity,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            viewModel.onBiometricUnlock()
                        }
                    })
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle(activity.getString(R.string.passcode_biometric_confirm))
                    .setSubtitle(activity.getString(R.string.passcode_biometric_title))
                    .setNegativeButtonText(activity.getString(R.string.passcode_biometric_cancel))
                    .build()
                prompt.authenticate(promptInfo)
            }
    }
}

@Composable
private fun BiometricButtonBlock(viewModel: UnlockPasscodeViewModel) {
    val biometricEnabled = viewModel.biometricEnabled.asStateValueNotNull()
    if (biometricEnabled) {
        PadIconButton(
            iconRes = Icons.Default.Fingerprint,
            rippleColor = ThemeData.current.onPrimary,
            onClick = viewModel::onBiometricRequest
        )
        Spacer16()
    }
}