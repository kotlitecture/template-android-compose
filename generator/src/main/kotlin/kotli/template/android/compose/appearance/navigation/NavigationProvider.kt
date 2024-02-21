package kotli.template.android.compose.appearance.navigation

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class NavigationProvider : BaseFeatureProvider() {

    override fun getId(): String = "navigation"
    override fun getType(): FeatureType = FeatureTypes.Appearance

    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}