package app.userflow.navigation.d

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.provideHiltViewModel

@Composable
fun NavigationDScreen(data: NavigationDDestination.Data?) {
    val viewModel: NavigationDViewModel = provideHiltViewModel()
    Box(Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "D"
        )
    }
}