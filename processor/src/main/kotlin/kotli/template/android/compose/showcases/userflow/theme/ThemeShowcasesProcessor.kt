package kotli.template.android.compose.showcases.userflow.theme

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.ShowcasesRules

object ThemeShowcasesProcessor : BaseFeatureProcessor() {

    const val ID = "showcases.userflow.theme"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            ShowcasesRules(
                RemoveMarkedLine("Userflow :: Theme")
            )
        )
    }

}