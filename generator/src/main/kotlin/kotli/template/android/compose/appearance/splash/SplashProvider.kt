package kotli.template.android.compose.appearance.splash

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class SplashProvider : BaseFeatureProvider() {

    override fun getId(): String = "splash"
    override fun getType(): FeatureType = FeatureTypes.Appearance

    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}