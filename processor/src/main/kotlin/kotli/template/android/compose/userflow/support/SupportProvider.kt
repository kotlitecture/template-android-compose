package kotli.template.android.compose.userflow.support

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class SupportProvider : BaseFeatureProvider() {

    override fun getId(): String = "support"
    override fun getType(): FeatureType = FeatureTypes.UserFlow

    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}