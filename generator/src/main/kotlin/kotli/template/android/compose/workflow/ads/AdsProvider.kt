package kotli.template.android.compose.workflow.ads

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class AdsProvider : FeatureProvider() {

    override val id: String = "ads"

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}