package kotli.template.android.compose.dataflow.config.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.config.basic.BasicConfigProcessor
import kotli.template.android.compose.unspecified.firebase.FirebaseProcessor

class FirebaseConfigProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://firebase.google.com/docs/remote-config"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/remote-config/get-started?platform=android"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        BasicConfigProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle",
            CleanupMarkedLine("{dataflow.config.firebase}", true)
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
            RemoveMarkedLine("{dataflow.config.firebase}", true)
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