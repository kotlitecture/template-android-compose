package kotli.template.android.compose.dataflow.work

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class WorkProvider : BaseFeatureProvider() {

    override fun getId(): String = "dataflow.work"
    override fun getType(): FeatureType = FeatureTypes.DataFlow
    override fun createProcessors(): List<FeatureProcessor> = listOf()

}