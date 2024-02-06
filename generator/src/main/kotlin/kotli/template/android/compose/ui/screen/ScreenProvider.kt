package kotli.template.android.compose.ui.screen

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class ScreenProvider : AbstractFeatureProvider() {

    override val id: String = "screen"
    override val multiple: Boolean = true
    override val type: FeatureType = FeatureType.UI

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}