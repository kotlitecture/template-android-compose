package kotli.template.android.compose.workflow.webtonative

import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext

class WebToNativeProcessor : FeatureProcessor() {

    override val id: String = ID

    override fun doApply(context: TemplateContext) {
        context.apply("settings.gradle") {
            cleanupLine("{workflow-webtonative}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{workflow-webtonative}")
        }
        context.apply("app/src/main/kotlin/app/AppActivity.kt") {
            cleanupLine("{workflow-webtonative}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("settings.gradle") {
            removeLine("{workflow-webtonative}")
        }
        context.apply("app/build.gradle") {
            removeLine("{workflow-webtonative}")
        }
        context.apply("app/src/main/kotlin/app/AppActivity.kt") {
            removeLine("{workflow-webtonative}")
        }
        context.apply("app/src/main/kotlin/app/feature/webtonative") {
            remove()
        }
        context.apply("workflow/webtonative") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("androidxWebkit")
        }
    }

    companion object {
        const val ID = "webtonative"
    }

}