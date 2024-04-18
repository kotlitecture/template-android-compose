package kotli.template.android.compose.showcases

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceMarkedText
import kotli.template.android.compose.NavigationBarStateRules
import kotli.template.android.compose.AppNavigationRouterRules
import kotli.template.android.compose.NavigationStateRules
import kotli.template.android.compose.ui.component.basic.BasicComponentsProcessor
import kotli.template.android.compose.ui.container.fixedheaderfooter.FixedHeaderFooterProcessor

object ShowcasesProcessor : BaseFeatureProcessor() {

    const val ID = "showcases"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        FixedHeaderFooterProcessor::class.java,
        BasicComponentsProcessor::class.java,
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            NavigationBarStateRules(
                CleanupMarkedBlock("{showcases.common}")
            )
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/showcases",
            RemoveFile()
        )
        state.onApplyRules(
            NavigationStateRules(
                RemoveMarkedLine("ShowcasesDestination")
            )
        )
        state.onApplyRules(
            NavigationBarStateRules(
                RemoveMarkedBlock("{showcases.common}"),
                RemoveMarkedLine("ShowcasesDestination")
            )
        )
        state.onApplyRules(
            AppNavigationRouterRules(
                RemoveMarkedLine("import app.showcases.ShowcasesDestination"),
                ReplaceMarkedText(
                    text = "ShowcasesDestination",
                    marker = "ShowcasesDestination",
                    replacer = "app.ui.screen.template.TemplateDestination"
                )
            )
        )
    }

}