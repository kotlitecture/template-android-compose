package kotli.template.android.compose.workflow.payments

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class PaymentsProvider : FeatureProvider() {

    override val id: String = "payments"

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}