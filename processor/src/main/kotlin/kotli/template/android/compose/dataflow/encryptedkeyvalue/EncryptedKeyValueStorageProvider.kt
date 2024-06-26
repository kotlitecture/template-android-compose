package kotli.template.android.compose.dataflow.encryptedkeyvalue

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.encryptedkeyvalue.sharedpreferences.EncryptedSharedPreferencesProcessor

object EncryptedKeyValueStorageProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.encryptedkeyvalue"

    override fun isMultiple(): Boolean = false

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        AppEncryptedKeyValueProcessor,
        EncryptedSharedPreferencesProcessor
    )

}