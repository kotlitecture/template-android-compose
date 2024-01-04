package kotli.template.android.compose.ui.screen

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class ScreenProvider : FeatureProvider() {

    override val id: String = "screen"

    override val type: FeatureType = FeatureType.UI

    override fun isMultiple(): Boolean = true

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}