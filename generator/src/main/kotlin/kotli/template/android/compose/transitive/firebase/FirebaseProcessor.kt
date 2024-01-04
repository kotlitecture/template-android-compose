package kotli.template.android.compose.transitive.firebase

import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext

class FirebaseProcessor : FeatureProcessor() {

    override val id: String = ID

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