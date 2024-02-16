package kotli.template.android.compose.userflow.support

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class SupportProvider : AbstractFeatureProvider() {

    override fun getId(): String = "support"
    override fun getType(): IFeatureType = FeatureType.UserFlow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}