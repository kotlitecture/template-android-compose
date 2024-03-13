package app.userflow.template

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.provideHiltViewModel
import core.ui.theme.material3.Material3ThemeData

@Composable
fun TemplateScreen() {
    val viewModel: TemplateViewModel = provideHiltViewModel()
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp)
                .statusBarsPadding()
            ,
            onClick = {},
            content = {
                Text(text = "TOP")
            }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = viewModel.javaClass.simpleName,
            color = Material3ThemeData.get().colorScheme.onSurface,
            fontWeight = FontWeight.W600,
            fontSize = 24.sp
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
            ,
            onClick = {},
            content = {
                Text(text = "BOTTOM")
            }
        )
    }
}