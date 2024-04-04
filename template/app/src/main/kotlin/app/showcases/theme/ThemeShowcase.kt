package app.showcases.theme

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import app.showcases.Showcase

object ThemeShowcase : Showcase() {

    override val id: String = "theme_showcase"
    override val label: String = "Theme"

    @Composable
    override fun ColumnScope.content() {

    }

}