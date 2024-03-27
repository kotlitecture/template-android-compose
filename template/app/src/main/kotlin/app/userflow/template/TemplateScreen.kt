package app.userflow.template

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.provideHiltViewModel
import app.ui.component.Spacer16
import app.ui.component.TopBarButton
import app.ui.container.FixedTopBarLayout
import core.ui.theme.material3.Material3ThemeData

@Composable
fun TemplateScreen(data: TemplateDestination.Data?) {
    val viewModel: TemplateViewModel = provideHiltViewModel(activityScoped = true)
    FixedTopBarLayout(
        title = viewModel.javaClass.simpleName,
        onBack = viewModel::onBack,
        actions = {
            TopBarButton(
                icon = Icons.Default.Call,
                onClick = {},
            )
            TopBarButton(
                icon = Icons.Default.Cake,
                onClick = {},
            )
        }
    ) {
        repeat(10) {
            Spacer16()
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = viewModel::onTop,
                content = {
                    Text(text = "TOP")
                }
            )
            Spacer16()
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = data?.title ?: "TemplateScreen",
                color = Material3ThemeData.current.colorScheme.onSurface,
                fontWeight = FontWeight.W600,
                fontSize = 24.sp
            )
            Spacer16()
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = viewModel::onBottom,
                content = {
                    Text(text = "BOTTOM")
                }
            )
            Spacer16()
        }
    }
}