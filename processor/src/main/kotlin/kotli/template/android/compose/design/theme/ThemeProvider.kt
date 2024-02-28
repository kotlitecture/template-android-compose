package kotli.template.android.compose.design.theme

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class ThemeProvider : BaseFeatureProvider() {

    override fun getId(): String = "theme"
    override fun getType(): FeatureType = FeatureTypes.Design

    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}