package kotli.template.android.compose.workflow.payments

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class PaymentsProvider : AbstractFeatureProvider() {

    override fun getId(): String = "payments"
    override fun getType(): IFeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}