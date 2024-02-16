package kotli.template.android.compose.userflow.update.market

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.applyVersionCatalog

class GooglePlayUpdateProcessor : AbstractFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.apply("settings.gradle") {
            cleanupLine("{market-update}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{market-update}")
        }
        context.apply("app/src/main/kotlin/app/AppActivity.kt") {
            cleanupLine("{market-update}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("settings.gradle") {
            removeLine("{market-update}")
        }
        context.apply("app/build.gradle") {
            removeLine("{market-update}")
        }
        context.apply("integration/market-update") {
            remove()
        }
        context.apply("app/src/main/kotlin/app/AppActivity.kt") {
            removeLine("{market-update}")
        }
        context.apply("app/src/main/kotlin/di/ProvidesUpdateSource.kt") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("googleAppUpdate")
        }
    }

    companion object {
        const val ID = "google-play-update"
    }

}