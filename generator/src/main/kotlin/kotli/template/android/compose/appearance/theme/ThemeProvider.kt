package kotli.template.android.compose.appearance.theme

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class ThemeProvider : AbstractFeatureProvider() {

    override fun getId(): String = "theme"
    override fun getType(): IFeatureType = FeatureType.Appearance

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}