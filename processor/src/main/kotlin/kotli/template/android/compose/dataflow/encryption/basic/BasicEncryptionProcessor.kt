package kotli.template.android.compose.dataflow.encryption.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.template.android.compose.dataflow.storage.encryptedkeyvalue.EncryptedKeyValueProcessor

class BasicEncryptionProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        EncryptedKeyValueProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("core/data/src/main/kotlin/core/data/datasource/encryption", RemoveFile())
        state.onApplyRules("*EncryptionSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.encryption.basic"
    }

}