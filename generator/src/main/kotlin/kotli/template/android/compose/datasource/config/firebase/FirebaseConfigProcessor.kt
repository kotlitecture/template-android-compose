package kotli.template.android.compose.datasource.config.firebase

import kotli.engine.FeatureProcessor
import kotli.engine.IFeatureProcessor
import kotli.engine.TemplateContext
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebaseConfigProcessor : FeatureProcessor() {

    private val appConfig = "app/src/main/kotlin/app/datasource/config/AppConfigSource.kt"
    private val appStartup = "app/src/main/kotlin/app/AppStartupInitializer.kt"

    override val id: String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://firebase.google.com/docs/remote-config"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/remote-config/get-started?platform=android"

    override fun dependencies(): List<Class<out IFeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.apply(appConfig) {
            cleanupLine("{firebase-config}")
        }
        context.apply(appStartup) {
            cleanupLine("{firebase-config}")
        }
        context.apply("settings.gradle") {
            cleanupLine("{firebase-config}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{firebase-config}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply(appConfig) {
            removeLine("{firebase-config}")
        }
        context.apply(appStartup) {
            removeLine("{firebase-config}")
        }
        context.apply("settings.gradle") {
            removeLine("{firebase-config}")
        }
        context.apply("app/build.gradle") {
            removeLine("{firebase-config}")
        }
        context.apply("integration/firebase-config") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("firebase-config")
        }
    }

    companion object {
        const val ID = "firebase-config"
    }

}