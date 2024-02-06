package kotli.template.android.compose.quality.startup.baselineprofile

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.TemplateContext

class BaselineProfileProcessor : AbstractFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://developer.android.com/topic/performance/baselineprofiles/overview"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://developer.android.com/topic/performance/baselineprofiles/configure-baselineprofiles"

    override fun doApply(context: TemplateContext) {
        context.apply("settings.gradle") {
            cleanupLine("{baselineprofile}")
        }
        context.apply("build.gradle") {
            cleanupLine("{baselineprofile}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{baselineprofile}")
            cleanupBlock("{baselineprofile-config}")
        }
        context.apply("baselineprofile/src/main/java/app/baselineprofile/BaselineProfileGenerator.kt") {
            replaceText("kotli.app") { context.layer.namespace }
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("settings.gradle") {
            removeLine("{baselineprofile}")
        }
        context.apply("build.gradle") {
            removeLine("{baselineprofile}")
        }
        context.apply("app/build.gradle") {
            removeLine("{baselineprofile}")
            removeBlock("{baselineprofile-config}")
        }
        context.apply("baselineprofile") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("benchmarkMacroJunit4")
            removeLine("baselineprofile")
            removeLine("uiautomator")
            removeLine("espresso")
        }
    }

    companion object {
        const val ID = "baseline-profile"
    }

}