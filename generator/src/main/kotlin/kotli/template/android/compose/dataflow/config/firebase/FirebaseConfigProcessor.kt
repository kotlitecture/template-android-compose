package kotli.template.android.compose.dataflow.config.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebaseConfigProcessor : BaseFeatureProcessor() {

    private val appConfig = "app/src/main/kotlin/app/datasource/config/AppConfigSource.kt"
    private val appStartup = "app/src/main/kotlin/app/AppStartupInitializer.kt"

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://firebase.google.com/docs/remote-config"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/remote-config/get-started?platform=android"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(appConfig, CleanupMarkedLine("{firebase-config}"))
        state.onApplyRules(appStartup, CleanupMarkedLine("{firebase-config}"))
        state.onApplyRules("settings.gradle", CleanupMarkedLine("{firebase-config}"))
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{firebase-config}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(appConfig, RemoveMarkedLine("{firebase-config}"))
        state.onApplyRules(appStartup, RemoveMarkedLine("{firebase-config}"))
        state.onApplyRules("settings.gradle", RemoveMarkedLine("{firebase-config}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{firebase-config}"))
        state.onApplyRules("integration/firebase-config", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("firebase-config")))
    }

    companion object {
        const val ID = "firebase-config"
    }

}