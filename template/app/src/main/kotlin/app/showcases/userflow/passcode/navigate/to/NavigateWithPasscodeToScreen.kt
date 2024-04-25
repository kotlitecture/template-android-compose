package app.showcases.userflow.passcode.navigate.to

import androidx.compose.runtime.Composable
import app.appViewModel
import app.ui.container.FixedTopBarColumnLayout

@Composable
fun NavigateWithPasscodeToScreen() {
    val viewModel: NavigateWithPasscodeToViewModel = appViewModel()
    FixedTopBarColumnLayout(
        title = "Target Destination",
        onBack = viewModel::onBack
    ) {
    }
}