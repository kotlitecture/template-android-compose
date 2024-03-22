package kotli.template.android.compose.devops.distribution.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.unspecified.firebase.FirebaseProcessor
import kotli.template.android.compose.unspecified.googleservices.GoogleServicesProcessor
import kotlin.time.Duration.Companion.hours

class FirebaseDistributionProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://firebase.google.com/docs/app-distribution"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/app-distribution/android/distribute-gradle"
    override fun getIntegrationEstimate(state: TemplateState): Long = 8.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle",
            CleanupMarkedLine("{devops.distribution.firebase}"),
            CleanupMarkedBlock("{devops.distribution.firebase.debug}"),
            CleanupMarkedBlock("{devops.distribution.firebase.staging}")
        )
        state.onApplyRules("build.gradle",
            CleanupMarkedLine("{devops.distribution.firebase}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle",
            RemoveMarkedLine("{devops.distribution.firebase}"),
            RemoveMarkedBlock("{devops.distribution.firebase.debug}"),
            RemoveMarkedBlock("{devops.distribution.firebase.staging}")
        )
        state.onApplyRules("build.gradle",
            RemoveMarkedLine("{devops.distribution.firebase}")
        )
        state.onApplyRules(
            VersionCatalogRules(RemoveMarkedLine("appdistribution"))
        )
    }

    companion object {
        const val ID = "devops.distribution.firebase"
    }

}