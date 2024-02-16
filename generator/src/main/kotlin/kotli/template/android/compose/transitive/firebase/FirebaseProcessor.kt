package kotli.template.android.compose.transitive.firebase

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.applyVersionCatalog

class FirebaseProcessor : AbstractFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
    }

    override fun doRemove(context: TemplateContext) {
        context.applyVersionCatalog {
            removeLine("firebaseBom")
        }
    }

    companion object {
        const val ID = "firebase"
    }

}