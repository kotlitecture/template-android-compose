package kotli.template.android.compose.wip

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.wip.flow.FlowApiProcessor

object WipProvider : BaseFeatureProvider() {

    override fun getId(): String = "wip"
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.Unspecified
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        FlowApiProcessor
    )

}