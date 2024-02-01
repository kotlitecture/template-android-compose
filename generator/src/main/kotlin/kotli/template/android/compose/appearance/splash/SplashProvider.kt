package kotli.template.android.compose.appearance.splash

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class SplashProvider : AbstractFeatureProvider() {

    override val id: String = "splash"

    override val type: FeatureType = FeatureType.Appearance

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}