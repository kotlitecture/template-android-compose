package kotli.template.android.compose.transitive.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine

class FirebaseProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("firebaseBom")))
    }

    companion object {
        const val ID = "firebase"
    }

}