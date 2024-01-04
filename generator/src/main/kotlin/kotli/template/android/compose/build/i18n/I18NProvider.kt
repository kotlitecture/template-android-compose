package kotli.template.android.compose.build.i18n

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class I18NProvider : FeatureProvider() {

    override val id: String = "i18n"

    override val type: FeatureType = FeatureType.Build

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}