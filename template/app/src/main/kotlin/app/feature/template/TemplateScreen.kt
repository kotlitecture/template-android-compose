package app.feature.template

import androidx.compose.runtime.Composable
import core.ui.layout.FixedTopBarLayout
import core.ui.mvvm.provideViewModel

@Composable
fun TemplateScreen() {
    val viewModel: TemplateViewModel = provideViewModel()
    FixedTopBarLayout(
        onBack = viewModel::onBack,
        title = viewModel.javaClass.simpleName
    ) {
    }
}