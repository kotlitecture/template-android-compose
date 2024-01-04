package core.ui.app.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppIcon {

    fun getFavIcon(active: Boolean): Any {
        return if (active) Icons.Default.Star else Icons.Default.StarOutline
    }

    @Composable
    fun getFavIconColor(active: Boolean): Color {
        return if (active) AppTheme.color.warning else AppTheme.color.iconPrimary
    }

    fun getCheckedIcon(active: Boolean): Any? {
        return if (active) Icons.Default.Check else null
    }

}