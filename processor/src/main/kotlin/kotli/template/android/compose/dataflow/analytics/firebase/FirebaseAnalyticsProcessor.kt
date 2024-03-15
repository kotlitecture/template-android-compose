package kotli.template.android.compose.dataflow.analytics.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.analytics.facade.FacadeAnalyticsProcessor
import kotli.template.android.compose.quality.crashes.firebase.FirebaseCrashlyticsProcessor
import kotli.template.android.compose.unspecified.firebase.FirebaseProcessor

class FirebaseAnalyticsProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://firebase.google.com/docs/analytics"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/analytics/get-started"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        FirebaseCrashlyticsProcessor::class.java,
        FacadeAnalyticsProcessor::class.java,
        FirebaseProcessor::class.java,
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{dataflow.analytics.firebase}", true)
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/datasource/analytics/AppAnalyticsSource.kt",
            RemoveMarkedLine("FirebaseAnalyticsSource")
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/datasource/analytics/firebase",
            RemoveFile()
        )
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{dataflow.analytics.firebase}", true)
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("firebase-analytics")
            )
        )
    }

    companion object {
        const val ID = "dataflow.analytics.firebase"
    }

}