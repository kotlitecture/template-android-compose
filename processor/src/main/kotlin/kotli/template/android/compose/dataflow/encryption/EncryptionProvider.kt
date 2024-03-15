package kotli.template.android.compose.dataflow.encryption

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.dataflow.encryption.basic.BasicEncryptionProcessor

class EncryptionProvider : BaseFeatureProvider() {

    override fun getId(): String = "dataflow.encryption"
    override fun getType(): FeatureType = FeatureTypes.DataFlow
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicEncryptionProcessor()
    )

}