package kotli.template.android.compose.dataflow

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.dataflow.common.CommonDataFlowProcessor

abstract class BaseDataFlowProvider : BaseFeatureProvider() {

    final override fun getType(): FeatureType = FeatureTypes.DataFlow
    final override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        CommonDataFlowProcessor::class.java
    )

}