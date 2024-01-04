package kotli.template.android.compose.appearance.theme

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class ThemeProvider : FeatureProvider() {

    override val id: String = "theme"

    override val type: FeatureType = FeatureType.Appearance

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}