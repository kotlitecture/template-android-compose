package kotli.template.android.compose.showcases.common

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceMarkedText
import kotli.template.android.compose.ui.component.basic.BasicComponentsProcessor
import kotli.template.android.compose.ui.container.fixedheaderfooter.FixedHeaderFooterProcessor

object CommonShowcasesProcessor : BaseFeatureProcessor() {

    const val ID = "showcases.common"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        FixedHeaderFooterProcessor::class.java,
        BasicComponentsProcessor::class.java,
    )

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