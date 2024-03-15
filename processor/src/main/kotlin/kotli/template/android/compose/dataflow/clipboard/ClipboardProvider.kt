package kotli.template.android.compose.dataflow.clipboard

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.dataflow.clipboard.basic.BasicClipboardProcessor

class ClipboardProvider : BaseFeatureProvider() {

    override fun getId(): String = "dataflow.clipboard"
    override fun getType(): FeatureType = FeatureTypes.DataFlow
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicClipboardProcessor()
    )

}