package kotli.template.android.compose.appearance.theme

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class ThemeProvider : BaseFeatureProvider() {

    override fun getId(): String = "theme"
    override fun getType(): FeatureType = FeatureTypes.Appearance

    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}