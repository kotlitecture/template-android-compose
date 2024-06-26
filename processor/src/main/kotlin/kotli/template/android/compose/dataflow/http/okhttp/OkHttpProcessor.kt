package kotli.template.android.compose.dataflow.http.okhttp

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.config.facade.FacadeConfigProcessor
import kotlin.time.Duration.Companion.minutes

class OkHttpProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://square.github.io/okhttp/"
    override fun getIntegrationUrl(state: TemplateState): String = "https://ktor.io/docs/client-create-new-application.html"
    override fun getIntegrationEstimate(state: TemplateState): Long = 30.minutes.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        FacadeConfigProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*HttpSource*", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("okhttp")))
        state.onApplyRules("app/src/main/kotlin/app/datasource/config/AppConfigSource.kt",
            RemoveMarkedLine("http_")
        )
    }

    companion object {
        const val ID = "dataflow.http.okhttp"
    }

}