package kotli.template.android.compose.ui.component.spacer

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class SpacerProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/component/Spacer.kt",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "ui.component.spacer"
    }

}