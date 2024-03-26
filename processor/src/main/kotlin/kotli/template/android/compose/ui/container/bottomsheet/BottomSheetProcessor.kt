package kotli.template.android.compose.ui.container.bottomsheet

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.ui.component.spacer.SpacerProcessor
import kotlin.time.Duration.Companion.minutes

class BottomSheetProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun getWebUrl(state: TemplateState): String = "https://github.com/workspace/bottomsheetdialog-compose"
    override fun getIntegrationUrl(state: TemplateState): String = "https://github.com/workspace/bottomsheetdialog-compose?tab=readme-ov-file#get-started"
    override fun getIntegrationEstimate(state: TemplateState): Long = 10.minutes.inWholeMilliseconds
    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        SpacerProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{ui.container.bottomsheet}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/container/bottomsheet",
            RemoveFile()
        )
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{ui.container.bottomsheet}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                listOf(
                    RemoveMarkedLine("bottomsheetdialogCompose")
                )
            )
        )
    }

    companion object {
        const val ID = "ui.container.bottomsheet"
    }

}