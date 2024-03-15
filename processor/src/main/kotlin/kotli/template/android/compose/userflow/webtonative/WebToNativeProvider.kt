package kotli.template.android.compose.userflow.webtonative

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class WebToNativeProvider : BaseFeatureProvider() {

    override fun getId(): String = "userflow.webtonative"
    override fun getType(): FeatureType = FeatureTypes.UserFlow
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        WebToNativeProcessor()
    )

}