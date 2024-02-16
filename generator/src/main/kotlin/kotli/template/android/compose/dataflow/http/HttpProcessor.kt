package kotli.template.android.compose.dataflow.http

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.TemplateContext

class HttpProcessor : AbstractFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.apply("app/src/main/kotlin/app/App.kt") {
            cleanupLine("{httpSource-import}")
            cleanupBlock("{httpSource-inject}")
            cleanupBlock("{httpSource-client}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("app/src/main/kotlin/di/ProvidesHttp.kt") {
            remove()
        }
        context.apply("app/src/main/kotlin/app/App.kt") {
            removeLine("{httpSource-import}")
            removeBlock("{httpSource-inject}")
            removeBlock("{httpSource-client}")
        }
    }

    companion object {
        const val ID = "http"
    }

}