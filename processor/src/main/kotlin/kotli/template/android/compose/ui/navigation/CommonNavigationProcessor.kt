package kotli.template.android.compose.ui.navigation

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class CommonNavigationProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/di/state/ProvidesNavigationBarState.kt",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "ui.navigation.common"
    }

}