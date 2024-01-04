package kotli.template.android.compose.workflow.support

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class SupportProvider : FeatureProvider() {

    override val id: String = "support"

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}