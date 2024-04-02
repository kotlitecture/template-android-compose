package kotli.template.android.compose.ui.container.motionlayout

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.minutes

class MotionLayoutProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 15.minutes.inWholeMilliseconds
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/develop/ui/views/animations/motionlayout"
    override fun getIntegrationUrl(state: TemplateState): String = "https://github.com/androidx/constraintlayout"

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            VersionCatalogRules(
                listOf(
                    RemoveMarkedLine("constraintlayout")
                )
            )
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/container/MotionLayout.kt",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "ui.container.motionlayout"
    }

}