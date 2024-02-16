package kotli.template.android.compose.quality.crashes.firebase

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.IFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.applyVersionCatalog
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebaseCrashlyticsProcessor : AbstractFeatureProcessor() {

    private val appStartup = "app/src/main/kotlin/app/AppStartupInitializer.kt"

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://firebase.google.com/docs/crashlytics"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/crashlytics/get-started?platform=android"

    override fun dependencies(): List<Class<out IFeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.apply(appStartup) {
            cleanupLine("{firebase-crashlytics}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{firebase-crashlytics}")
        }
        context.apply("build.gradle") {
            cleanupLine("{firebase-crashlytics}")
        }
        context.apply("settings.gradle") {
            cleanupLine("{firebase-crashlytics}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{firebase-crashlytics}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply(appStartup) {
            removeLine("{firebase-crashlytics}")
        }
        context.apply("app/build.gradle") {
            removeLine("{firebase-crashlytics}")
        }
        context.apply("build.gradle") {
            removeLine("{firebase-crashlytics}")
        }
        context.apply("settings.gradle") {
            removeLine("{firebase-crashlytics}")
        }
        context.apply("app/build.gradle") {
            removeLine("{firebase-crashlytics}")
        }
        context.apply("integration/firebase-crashlytics") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("firebase-crashlytics")
        }
    }

    companion object {
        const val ID = "firebase-crashlytics"
    }

}