package kotli.template.android.compose.ui.screen

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class ScreenProvider : AbstractFeatureProvider() {

    override fun getId(): String = "screen"
    override fun isMultiple(): Boolean = true
    override fun getType(): IFeatureType = FeatureType.UI

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}