package kotli.template.android.compose.appearance.navigation

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class NavigationProvider : AbstractFeatureProvider() {

    override val id: String = "navigation"

    override val type: FeatureType = FeatureType.Appearance

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}