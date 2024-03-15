package kotli.template.android.compose.userflow.payments

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class PaymentsProvider : BaseFeatureProvider() {

    override fun getId(): String = "userflow.payments"
    override fun getType(): FeatureType = FeatureTypes.UserFlow
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}