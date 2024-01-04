package kotli.template.android.compose.workflow.webtonative

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class WebToNativeProvider : FeatureProvider() {

    override val id: String = "webtonative"

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        WebToNativeProcessor()
    )
}