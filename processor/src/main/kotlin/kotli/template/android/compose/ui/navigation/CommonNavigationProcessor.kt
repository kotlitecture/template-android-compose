package kotli.template.android.compose.ui.navigation

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class CommonNavigationProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/AppActivity.kt",
            CleanupMarkedLine("{ui.navigation.common}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/di/state/ProvidesNavigationBarState.kt",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/AppActivity.kt",
            RemoveMarkedLine("{ui.navigation.common}"),
            RemoveMarkedLine("NavigationBarProvider"),
        )
    }

    companion object {
        const val ID = "ui.navigation.common"
    }

}