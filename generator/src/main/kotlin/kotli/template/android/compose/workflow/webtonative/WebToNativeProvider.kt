package kotli.template.android.compose.workflow.webtonative

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class WebToNativeProvider : AbstractFeatureProvider() {

    override val id: String = "webtonative"

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        WebToNativeProcessor()
    )
}