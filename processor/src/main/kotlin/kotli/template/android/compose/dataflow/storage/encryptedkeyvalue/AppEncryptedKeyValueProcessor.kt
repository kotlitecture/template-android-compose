package kotli.template.android.compose.dataflow.storage.encryptedkeyvalue

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.template.android.compose.dataflow.storage.keyvalue.CoreKeyValueProcessor

object AppEncryptedKeyValueProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.storage.encryptedkeyvalue.app"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        CoreKeyValueProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*EncryptedKeyValueSource*", RemoveFile())
    }

}