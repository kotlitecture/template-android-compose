package kotli.template.android.compose.testing.logging.tinylog

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class TinylogProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://tinylog.org/v2/"
    override fun getIntegrationUrl(state: TemplateState): String = "https://tinylog.org/v2/getting-started/"

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{testing.logging.tinylog}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{testing.logging.tinylog}")
        )
        state.onApplyRules(
            "*/tinylog.properties",
            RemoveFile()
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("tinylog")
            )
        )
    }

    companion object {
        const val ID = "testing.logging.tinylog"
    }

}