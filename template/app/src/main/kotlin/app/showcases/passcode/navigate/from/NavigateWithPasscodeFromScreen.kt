package app.showcases.passcode.navigate.from

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.provideHiltViewModel
import app.ui.container.FixedTopBarColumnLayout

@Composable
fun NavigateWithPasscodeFromScreen() {
    val viewModel: NavigateWithPasscodeFromViewModel = provideHiltViewModel()
    FixedTopBarColumnLayout(
        title = "Source Destination",
        onBack = viewModel::onBack
    ) {
        OutlinedCard(
            modifier = Modifier.padding(16.dp),
            content = {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = """
                    If the passcode is enabled, the user will first be navigated to the [NavigateWithPasscodeToDestination] to confirm their credentials.
                    
                    Either way, they will then be navigated to the provided destination.
                    """.trimIndent()
                )
            }
        )
        ElevatedButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = viewModel::onNavigateToTarget,
            content = { Text(text = "Target Destination") }
        )
    }
}