package kotli.template.android.compose.ui.navigation

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class UiNavigationProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.navigation"
    override fun getType(): FeatureType = FeatureTypes.UI
    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}