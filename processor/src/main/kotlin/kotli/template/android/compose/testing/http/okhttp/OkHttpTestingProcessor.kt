package kotli.template.android.compose.testing.http.okhttp

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.http.okhttp.OkHttpProcessor
import kotlin.time.Duration.Companion.minutes

class OkHttpTestingProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://github.com/ChuckerTeam/chucker"
    override fun getIntegrationUrl(state: TemplateState): String = "https://github.com/ChuckerTeam/chucker?tab=readme-ov-file#getting-started-"
    override fun getIntegrationEstimate(state: TemplateState): Long = 20.minutes.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        OkHttpProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{testing.http.okhttp}")
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/di/ProvidesOkHttpSource.kt",
            CleanupMarkedBlock("{testing.http.okhttp}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{testing.http.okhttp}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("chucker")
            )
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/di/ProvidesOkHttpSource.kt",
            RemoveMarkedBlock("{testing.http.okhttp}"),
            RemoveMarkedLine("chucker")
        )
        state.onApplyRules(
            "*chucker*",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "testing.http.okhttp"
    }

}