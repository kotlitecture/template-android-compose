package kotli.template.android.compose.testing.loadtests.gatling

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine

class GatlingProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules("build.gradle", CleanupMarkedLine("{testing.loadtests.gatling}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("build.gradle", RemoveMarkedLine("{testing.loadtests.gatling}"))
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("gatling")))
    }

    companion object {
        const val ID = "testing.loadtests.gatling"
    }

}