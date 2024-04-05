package app.showcases

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.provideHiltViewModel
import app.ui.component.basic.ActionButton
import app.ui.component.basic.AnyIcon
import app.ui.component.basic.Spacer16
import app.ui.container.FixedHeaderFooterLazyColumnLayout
import core.ui.state.StoreObject

@Composable
fun ShowcasesScreen() {
    val viewModel: ShowcasesViewModel = provideHiltViewModel()
    val showcasesState = viewModel.showcasesStore.asStateNotNull()
    FixedHeaderFooterLazyColumnLayout(
        header = {
            ActionButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End),
                icon = Icons.Default.QuestionMark,
                onClick = viewModel::onShowHint,
            )
        },
        content = {
            item { Spacer16() }
            showcasesState.value.forEach { showcase ->
                showcase(showcase) { showcase.onClick(viewModel) }
            }
            item { Spacer16() }
        }
    )
    HintBlock(viewModel.hintStore)
}

@Composable
private fun HintBlock(hintStore: StoreObject<Boolean>) {
    if (!hintStore.asStateValueNotNull()) return
    AlertDialog(
        onDismissRequest = hintStore::clear,
        text = {
            Text(
                text = """
                Showcases are utilized to demonstrate features included in the generated project structure.
                
                Once everything is clear and you no longer require this screen, proceed with deletion:

                1. Package `app/showcases`.
                
                2. Usage of `ShowcasesDestination` in `app.AppViewModel`.
                
                3. Usage of `ShowcasesDestination` in `app.di.state.ProvidesNavigationState`.
                
                4. Usage of `ShowcasesDestination` in `app.di.state.ProvidesNavigationBarState`.
            """.trimIndent()
            )
        },
        confirmButton = {
            TextButton(onClick = hintStore::clear) {
                Text(text = "Got it!")
            }
        }
    )
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