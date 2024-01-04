package kotli.template.android.compose.appearance.splash

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class SplashProvider : FeatureProvider() {

    override val id: String = "splash"

    override val type: FeatureType = FeatureType.Appearance

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}