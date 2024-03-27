package app.userflow.template

import androidx.compose.runtime.Composable
import app.provideHiltViewModel
import app.ui.container.FixedTopBarLayout

@Composable
fun TemplateScreen(data: TemplateDestination.Data?) {
    val viewModel: TemplateViewModel = provideHiltViewModel(activityScoped = true)
    FixedTopBarLayout(
        title = data?.title ?: viewModel.javaClass.simpleName,
        onBack = viewModel::onBack
    ) {
    }
}