package kotli.template.android.compose.dataflow.database.objectbox

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.hours

object ObjectBoxProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.database.objectbox"

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://objectbox.io"
    override fun getIntegrationUrl(state: TemplateState): String =
        "https://docs.objectbox.io/getting-started"

    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "build.gradle",
            CleanupMarkedLine("{dataflow.database.objectbox}")
        )
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{dataflow.database.objectbox}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "build.gradle",
            RemoveMarkedLine("{dataflow.database.objectbox}")
        )
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{dataflow.database.objectbox}")
        )
        state.onApplyRules(
            VersionCatalogRules(RemoveMarkedLine("objectbox"))
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/di/datasource/ProvidesObjectBoxSource.kt",
            RemoveFile()
        )
        state.onApplyRules(
            "*/database/objectbox/*",
            RemoveFile()
        )
        state.onApplyRules(
            "app/objectbox-models",
            RemoveFile()
        )
    }

}