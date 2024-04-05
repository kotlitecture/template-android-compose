package app.userflow.theme.toggle

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.provideHiltViewModel
import app.ui.component.basic.ActionButton

/**
 * Composable function for rendering a button that toggles the theme.
 *
 * @param modifier The modifier to be applied to the button.
 */
@Composable
fun ToggleThemeButton(modifier: Modifier = Modifier) {
    val viewModel: ToggleThemeViewModel = provideHiltViewModel()
    val data = viewModel.dataStore.asStateValue() ?: return
    ActionButton(
        modifier = modifier,
        onClick = viewModel::onToggle,
        icon = data.getIcon()
    )
}