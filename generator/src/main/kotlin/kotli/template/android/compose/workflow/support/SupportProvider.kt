package kotli.template.android.compose.workflow.support

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class SupportProvider : AbstractFeatureProvider() {

    override val id: String = "support"
    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}