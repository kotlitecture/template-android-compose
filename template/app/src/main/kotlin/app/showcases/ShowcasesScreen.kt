package app.showcases

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.provideHiltViewModel
import app.ui.component.basic.AnyIcon
import app.ui.component.basic.SpacerNavigationBar
import app.ui.component.basic.SpacerStatusBar
import core.ui.theme.material3.Material3ThemeData

@Composable
fun ShowcasesScreen() {
    val viewModel: ShowcasesViewModel = provideHiltViewModel()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { SpacerStatusBar() }
        header()
        viewModel.showcases.forEach { showcase ->
            showcase(showcase, viewModel::onOpen)
        }
        item { SpacerNavigationBar() }
    }
}

private fun LazyListScope.header() {
    item {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Material3ThemeData.current.colorScheme.surfaceBright)
                .padding(16.dp),
            fontSize = 14.sp,
            text = """
                Showcases are utilized to demonstrate features included in the generated project structure.
                
                Once everything is clear and you no longer require this screen, proceed with deletion:

                1. Package `app/showcases`.
                
                2. Usage of `ShowcasesDestination` in `app.AppViewModel`.
                
                3. Usage of `ShowcasesDestination` in `app.di.state.ProvidesNavigationState`.
                
                4. Usage of `ShowcasesDestination` in `app.di.state.ProvidesNavigationBarState`.
            """.trimIndent()
        )
    }
}

private fun LazyListScope.showcase(
    showcase: Showcase,
    onClick: (showcase: Showcase) -> Unit
) {
    item {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(showcase) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = showcase.label)
                AnyIcon(model = Icons.Default.ChevronRight)
            }
        }
    }
}