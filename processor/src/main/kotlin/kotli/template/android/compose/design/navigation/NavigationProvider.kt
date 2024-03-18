package kotli.template.android.compose.design.navigation

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class NavigationProvider : BaseFeatureProvider() {

    override fun getId(): String = "design.navigation"
    override fun getType(): FeatureType = FeatureTypes.Design
    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}