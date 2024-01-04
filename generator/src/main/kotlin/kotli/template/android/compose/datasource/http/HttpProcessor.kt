package kotli.template.android.compose.datasource.http

import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext

class HttpProcessor : FeatureProcessor() {

    override val id: String = ID

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