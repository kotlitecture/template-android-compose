package kotli.template.android.compose.dataflow.biometric

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.biometric.basic.BasicBiometricProcessor

class BiometricProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.biometric"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicBiometricProcessor()
    )

}