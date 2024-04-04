package kotli.template.android.compose.showcases.common

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceMarkedText

object CommonShowcasesProcessor : BaseFeatureProcessor() {

    const val ID = "showcases.common"

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/di/state/ProvidesNavigationBarState.kt",
            CleanupMarkedBlock("{showcases.common}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/showcases",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/di/state/ProvidesNavigationState.kt",
            RemoveMarkedLine("ShowcasesDestination")
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/di/state/ProvidesNavigationBarState.kt",
            RemoveMarkedBlock("{showcases.common}"),
            RemoveMarkedLine("ShowcasesDestination")
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/AppViewModel.kt",
            ReplaceMarkedText(
                text = "ShowcasesDestination",
                marker = "setStartDestination",
                replacer = "app.ui.screen.template.TemplateDestination"
            ),
            RemoveMarkedLine("ShowcasesDestination")
        )
    }

}