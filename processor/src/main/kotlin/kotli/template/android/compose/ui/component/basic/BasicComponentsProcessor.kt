package kotli.template.android.compose.ui.component.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class BasicComponentsProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/component/Spacer.kt",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/component/Icon.kt",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/component/Button.kt",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "ui.component.basic"
    }

}