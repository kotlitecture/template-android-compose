package kotli.template.android.compose.ui.command.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class BasicCommandProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/command",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/AppActivity.kt",
            RemoveMarkedLine("CommandProvider"),
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/di/state/ProvidesCommandState.kt",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "ui.command.basic"
    }

}