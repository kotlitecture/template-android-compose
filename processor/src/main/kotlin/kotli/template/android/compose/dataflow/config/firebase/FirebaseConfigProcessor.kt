package kotli.template.android.compose.dataflow.config.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.config.facade.FacadeConfigProcessor
import kotli.template.android.compose.unspecified.firebase.FirebaseProcessor
import kotlin.time.Duration.Companion.hours

class FirebaseConfigProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://firebase.google.com/docs/remote-config"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/remote-config/get-started?platform=android"
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        FacadeConfigProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle",
            CleanupMarkedLine("{dataflow.config.firebase}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/src/main/kotlin/app/datasource/config/AppConfigSource.kt",
            RemoveMarkedLine("FirebaseRemoteConfigSource")
        )
        state.onApplyRules("app/src/main/kotlin/app/datasource/config/firebase",
            RemoveFile()
        )
        state.onApplyRules("app/build.gradle",
            RemoveMarkedLine("{dataflow.config.firebase}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("firebase-config")
            )
        )
    }

    companion object {
        const val ID = "dataflow.config.firebase"
    }

}