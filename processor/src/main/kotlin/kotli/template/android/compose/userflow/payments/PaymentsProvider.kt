package kotli.template.android.compose.userflow.payments

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider

class PaymentsProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.payments"
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}