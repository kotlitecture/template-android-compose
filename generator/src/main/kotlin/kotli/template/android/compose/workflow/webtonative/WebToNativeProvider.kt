package kotli.template.android.compose.workflow.webtonative

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class WebToNativeProvider : AbstractFeatureProvider() {

    override fun getId(): String = "webtonative"
    override fun getType(): IFeatureType = FeatureType.UserFlow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        WebToNativeProcessor()
    )
}