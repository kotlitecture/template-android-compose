package app.userflow.template

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.ui.provideViewModel

@Composable
fun TemplateScreen() {
    val viewModel: TemplateViewModel = provideViewModel()
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = viewModel.javaClass.simpleName
        )
    }
}