package kotli.template.android.compose.dataflow.keyvalue

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

object AppKeyValueProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.keyvalue.app"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        CoreKeyValueProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/src/main/kotlin/app/datasource/keyvalue/AppKeyValueSource.kt", RemoveFile())
        state.onApplyRules("app/src/main/kotlin/app/di/datasource/ProvidesKeyValueSource.kt", RemoveFile())
    }

}