package app.userflow.passcode.ui.enable.biometric

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.R
import app.provideHiltViewModel
import app.ui.component.basic.Spacer16
import app.ui.container.FixedTopBarColumnLayout

@Composable
fun SetBiometricScreen() {
    val viewModel: SetBiometricViewModel = provideHiltViewModel()
    FixedTopBarColumnLayout(
        onBack = viewModel::onBack
    ) {
        Spacer16()

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.passcode_biometric_enable_text),
            fontWeight = FontWeight.W600,
            fontSize = 24.sp,
        )

        Spacer(Modifier.weight(1f))

        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = viewModel::onEnable,
            content = {
                Text(text = stringResource(R.string.passcode_biometric_enable_yes))
            }
        )

        Spacer16()

        TextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = viewModel::onSkip,
            content = {
                Text(text = stringResource(R.string.passcode_biometric_enable_no))
            }
        )

        Spacer16()
    }
}