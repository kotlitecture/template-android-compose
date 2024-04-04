package app.showcases

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.provideHiltViewModel

@Composable
fun ShowcasesScreen() {
    val viewModel: ShowcasesViewModel = provideHiltViewModel()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        spacerStatusBar()
        header()
        spacerNavigationBar()
    }
}

private fun LazyListScope.spacerStatusBar() {
    item { Spacer(modifier = Modifier.statusBarsPadding()) }
}

private fun LazyListScope.header() {
    item {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Showcases",
            fontSize = 18.sp
        )
    }
}

private fun LazyListScope.spacerNavigationBar() {
    item { Spacer(modifier = Modifier.navigationBarsPadding()) }
}