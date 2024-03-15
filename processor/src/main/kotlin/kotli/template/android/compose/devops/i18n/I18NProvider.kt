package kotli.template.android.compose.devops.i18n

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class I18NProvider : BaseFeatureProvider() {

    override fun getId(): String = "devops.i18n"
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.DevOps
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}