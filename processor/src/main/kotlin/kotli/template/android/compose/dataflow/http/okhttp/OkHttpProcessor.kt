package kotli.template.android.compose.dataflow.http.okhttp

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.config.basic.BasicConfigProcessor

class OkHttpProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        BasicConfigProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*HttpSource*", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("okhttp")))
        state.onApplyRules("app/src/main/kotlin/app/datasource/config/AppConfigSource.kt",
            RemoveMarkedLine("api_")
        )
    }

    companion object {
        const val ID = "dataflow.http.okhttp"
    }

}