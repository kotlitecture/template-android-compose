package kotli.template.android.compose.ui.screen

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class UiScreenProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.screen"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.UI
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}