package kotli.template.android.compose.dataflow.storage.encryptedkeyvalue

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.storage.keyvalue.KeyValueProcessor

class EncryptedKeyValueProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        KeyValueProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules("core/data/build.gradle", CleanupMarkedLine("{dataflow.storage.encrypted-key-value}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("core/data/build.gradle", RemoveMarkedLine("{dataflow.storage.encrypted-key-value}"))
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("androidxSecurityCrypto")))
        state.onApplyRules("*EncryptedKeyValueSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.storage.encrypted-key-value"
    }

}