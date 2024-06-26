package app.userflow.navigation.c

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.appViewModel

@Composable
fun NavigationCScreen() {
    val viewModel: NavigationCViewModel = appViewModel()
    Box(Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "C"
        )
    }
}