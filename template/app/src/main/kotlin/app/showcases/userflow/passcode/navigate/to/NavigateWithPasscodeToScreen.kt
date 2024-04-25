package app.showcases.userflow.passcode.navigate.to

import androidx.compose.runtime.Composable
import app.provideHiltViewModel
import app.ui.container.FixedTopBarColumnLayout

@Composable
fun NavigateWithPasscodeToScreen() {
    val viewModel: NavigateWithPasscodeToViewModel = provideHiltViewModel()
    FixedTopBarColumnLayout(
        title = "Target Destination",
        onBack = viewModel::onBack
    ) {
    }
}