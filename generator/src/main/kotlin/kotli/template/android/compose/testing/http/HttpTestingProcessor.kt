package kotli.template.android.compose.testing.http

import kotli.engine.FeatureProcessor
import kotli.engine.IFeatureProcessor
import kotli.engine.TemplateContext
import kotli.template.android.compose.datasource.http.HttpProcessor

class HttpTestingProcessor : FeatureProcessor() {

    override val id: String = ID

    override fun dependencies(): List<Class<out IFeatureProcessor>> = listOf(
        HttpProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.apply("settings.gradle") {
            cleanupLine("{testing-http}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{testing-http}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("settings.gradle") {
            removeLine("{testing-http}")
        }
        context.apply("app/build.gradle") {
            removeLine("{testing-http}")
        }
        context.apply("core/testing-http") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("chucker")
        }
    }

    companion object {
        const val ID = "testing-http"
    }

}