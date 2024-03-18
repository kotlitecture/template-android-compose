package kotli.template.android.compose.dataflow.encryption

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.encryption.basic.BasicEncryptionProcessor

class EncryptionProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.encryption"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicEncryptionProcessor()
    )

}