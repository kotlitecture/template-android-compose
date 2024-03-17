package kotli.template.android.compose.userflow.loader.data

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.config.basic.BasicConfigProcessor

class DataLoaderProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        BasicConfigProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*DataLoader*", RemoveFile())
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("DataLoaderProvider"))
        state.onApplyRules("app/src/main/kotlin/app/datasource/config/AppConfigSource.kt",
            RemoveMarkedLine("ui_loading")
        )
    }

    companion object {
        const val ID = "userflow.loader.data"
    }

}