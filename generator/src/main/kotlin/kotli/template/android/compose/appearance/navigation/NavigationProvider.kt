package kotli.template.android.compose.appearance.navigation

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class NavigationProvider : AbstractFeatureProvider() {

    override fun getId(): String = "navigation"
    override fun getType(): IFeatureType = FeatureType.Appearance

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}