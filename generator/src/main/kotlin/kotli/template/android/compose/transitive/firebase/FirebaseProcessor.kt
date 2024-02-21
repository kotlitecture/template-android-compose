package kotli.template.android.compose.transitive.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.RemoveMarkedLine

class FirebaseProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyVersionCatalogRules(RemoveMarkedLine("firebaseBom"))
    }

    companion object {
        const val ID = "firebase"
    }

}