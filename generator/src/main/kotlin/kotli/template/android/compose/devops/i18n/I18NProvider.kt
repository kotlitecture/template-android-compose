package kotli.template.android.compose.devops.i18n

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class I18NProvider : AbstractFeatureProvider() {

    override fun getId(): String = "i18n"
    override fun isMultiple(): Boolean = false
    override fun getType(): IFeatureType = FeatureType.DevOps

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}