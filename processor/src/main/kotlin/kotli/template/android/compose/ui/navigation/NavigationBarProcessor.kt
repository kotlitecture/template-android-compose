package kotli.template.android.compose.ui.navigation

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.AppActivityRules
import kotli.template.android.compose.NavigationBarStateRules
import kotli.template.android.compose.NavigationStateRules
import kotli.template.android.compose.ui.component.basic.BasicComponentsProcessor

object NavigationBarProcessor : BaseFeatureProcessor() {

    const val ID = "ui.navigation.common"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        BasicComponentsProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            AppActivityRules(
                CleanupMarkedLine("{ui.navigation.common}")
            )
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/userflow/navigation",
            RemoveFile()
        )
        state.onApplyRules(
            NavigationBarStateRules(
                RemoveFile()
            )
        )
        state.onApplyRules(
            NavigationStateRules(
                RemoveMarkedLine("NavigationADestination"),
                RemoveMarkedLine("NavigationBDestination"),
                RemoveMarkedLine("NavigationCDestination"),
                RemoveMarkedLine("NavigationDDestination")
            )
        )
        state.onApplyRules(
            AppActivityRules(
                RemoveMarkedLine("{ui.navigation.common}"),
                RemoveMarkedLine("NavigationBarProvider")
            )
        )
    }

}