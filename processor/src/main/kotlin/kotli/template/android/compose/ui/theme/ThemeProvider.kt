package kotli.template.android.compose.ui.theme

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class ThemeProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.theme"
    override fun getType(): FeatureType = FeatureTypes.UI
    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}