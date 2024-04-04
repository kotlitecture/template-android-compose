package app.showcases

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.provideHiltViewModel
import app.ui.component.basic.AnyIcon
import app.ui.component.basic.Spacer16
import app.ui.component.basic.SpacerNavigationBar
import app.ui.component.basic.SpacerStatusBar

@Composable
fun ShowcasesScreen() {
    val viewModel: ShowcasesViewModel = provideHiltViewModel()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { SpacerStatusBar() }
        item { Spacer16() }
        header()
        item { Spacer16() }
        viewModel.showcases.forEach { showcase ->
            showcase(showcase) { showcase.onClick(viewModel) }
        }
        item { Spacer16() }
        item { SpacerNavigationBar() }
    }
}

private fun LazyListScope.header() {
    item {
        ElevatedCard(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
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
}

private fun LazyListScope.showcase(
    showcase: Showcase,
    onClick: () -> Unit
) {
    item {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
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