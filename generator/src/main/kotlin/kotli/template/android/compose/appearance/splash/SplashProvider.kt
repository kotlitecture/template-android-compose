package kotli.template.android.compose.appearance.splash

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class SplashProvider : AbstractFeatureProvider() {

    override fun getId(): String = "splash"
    override fun getType(): IFeatureType = FeatureType.Appearance

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}