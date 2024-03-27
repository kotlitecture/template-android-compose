package app.ui.component

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.ui.theme.ThemeData

/**
 * Composable function for rendering a button in the top app bar.
 *
 * @param modifier The modifier to be applied to the button.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param icon The icon to be displayed on the button.
 * @param tint The color to tint the icon.
 */
@Composable
fun TopBarButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: Any?,
    tint: Color = ThemeData.current.onPrimary
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            AnyIcon(
                model = icon,
                tint = tint
            )
        }
    )
}