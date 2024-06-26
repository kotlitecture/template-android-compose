package kotli.template.android.compose.showcases.userflow.passcode

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.ShowcasesRules

object PasscodeShowcasesProcessor : BaseFeatureProcessor() {

    const val ID = "showcases.userflow.passcode"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            ShowcasesRules(
                RemoveMarkedLine("Passcode")
            )
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/showcases/userflow/passcode",
            RemoveFile()
        )
    }

}