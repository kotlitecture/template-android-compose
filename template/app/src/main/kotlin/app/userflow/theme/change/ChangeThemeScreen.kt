package app.userflow.theme.change

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.provideHiltViewModel
import app.ui.component.basic.Spacer8
import app.ui.container.FixedTopBarLayout
import core.ui.theme.ThemeData

@Composable
fun ChangeThemeScreen() {
    val viewModel: ChangeThemeViewModel = provideHiltViewModel()
    FixedTopBarLayout(
        title = "Change theme",
        onBack = viewModel::onBack,
        content = {
            ChangeThemeLayout(
                modifier = Modifier.padding(16.dp),
                viewModel = viewModel
            )
        }
    )
}

@Composable
fun ChangeThemeDialog() {
    val viewModel: ChangeThemeViewModel = provideHiltViewModel()
    ChangeThemeLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(ThemeData.current.primary)
            .padding(24.dp),
        viewModel = viewModel
    )
}

@Composable
fun ChangeThemeLayout(
    modifier: Modifier = Modifier,
    viewModel: ChangeThemeViewModel = provideHiltViewModel()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DynamicColorBlock()
        DarkModePreferenceBlock(viewModel)
    }
}

@Composable
fun DynamicColorBlock(viewModel: ChangeThemeViewModel = provideHiltViewModel()) {
    val use = viewModel.dynamicColorsStore.asStateValue() ?: return
    Column {
        HeaderBlock("Use Dynamic Color")
        Spacer8()
        ToggleBlock(
            label = "Yes",
            selected = use,
            onClick = viewModel::onEnableDynamicColors
        )
        ToggleBlock(
            label = "No",
            selected = !use,
            onClick = viewModel::onDisableDynamicColors
        )
    }
}

@Composable
fun DarkModePreferenceBlock(viewModel: ChangeThemeViewModel = provideHiltViewModel()) {
    val config = viewModel.configStore.asStateValue() ?: return
    Column {
        HeaderBlock("Dark mode preference")
        Spacer8()
        ToggleBlock(
            label = "System default",
            selected = config.autoDark,
            onClick = viewModel::onUseSystemDefault
        )
        ToggleBlock(
            label = "Light",
            selected = !config.autoDark && !config.defaultTheme.dark,
            onClick = viewModel::onUseLight
        )
        ToggleBlock(
            label = "Dark",
            selected = !config.autoDark && config.defaultTheme.dark,
            onClick = viewModel::onUseDark
        )
    }
}

@Composable
fun HeaderBlock(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.W600
    )
}

@Composable
fun ToggleBlock(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(text = label)
    }
}