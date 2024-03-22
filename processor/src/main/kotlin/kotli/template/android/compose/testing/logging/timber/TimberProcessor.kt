package kotli.template.android.compose.testing.logging.timber

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.unspecified.startup.StartupInitializerProcessor
import kotlin.time.Duration.Companion.minutes

class TimberProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://github.com/JakeWharton/timber"
    override fun getIntegrationEstimate(state: TemplateState): Long = 15.minutes.inWholeMilliseconds
    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        StartupInitializerProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{testing.logging.timber}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{testing.logging.timber}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("timber")
            )
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/AppStartupInitializer.kt",
            RemoveMarkedLine("TimberInitializer")
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/datasource/logging/TimberInitializer.kt",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "testing.logging.timber"
    }

}