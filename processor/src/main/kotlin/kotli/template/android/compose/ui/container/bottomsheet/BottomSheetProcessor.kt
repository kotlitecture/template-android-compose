package kotli.template.android.compose.ui.container.bottomsheet

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotlin.time.Duration.Companion.minutes

class BottomSheetProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 10.minutes.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/container/BottomSheetLayout.kt",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "ui.container.bottomsheet"
    }

}