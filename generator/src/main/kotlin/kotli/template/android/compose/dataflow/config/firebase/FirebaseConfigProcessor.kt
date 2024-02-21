package kotli.template.android.compose.dataflow.config.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebaseConfigProcessor : BaseFeatureProcessor() {

    private val appConfig = "app/src/main/kotlin/app/datasource/config/AppConfigSource.kt"
    private val appStartup = "app/src/main/kotlin/app/AppStartupInitializer.kt"

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://firebase.google.com/docs/remote-config"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/remote-config/get-started?platform=android"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.onApplyRule(appConfig, CleanupMarkedLine("{firebase-config}"))
        context.onApplyRule(appStartup, CleanupMarkedLine("{firebase-config}"))
        context.onApplyRule("settings.gradle", CleanupMarkedLine("{firebase-config}"))
        context.onApplyRule("app/build.gradle", CleanupMarkedLine("{firebase-config}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule(appConfig, RemoveMarkedLine("{firebase-config}"))
        context.onApplyRule(appStartup, RemoveMarkedLine("{firebase-config}"))
        context.onApplyRule("settings.gradle", RemoveMarkedLine("{firebase-config}"))
        context.onApplyRule("app/build.gradle", RemoveMarkedLine("{firebase-config}"))
        context.onApplyRule("integration/firebase-config", RemoveFile())
        context.onAddVersionCatalogRules(RemoveMarkedLine("firebase-config"))
    }

    companion object {
        const val ID = "firebase-config"
    }

}