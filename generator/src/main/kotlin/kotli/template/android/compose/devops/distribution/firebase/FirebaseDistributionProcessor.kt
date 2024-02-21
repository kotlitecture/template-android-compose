package kotli.template.android.compose.devops.distribution.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebaseDistributionProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://firebase.google.com/docs/app-distribution"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/app-distribution/android/distribute-gradle"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("app/build.gradle",
            CleanupMarkedLine("{firebase-distribution}"),
            CleanupMarkedBlock("{firebase-distribution-debug}"),
            CleanupMarkedBlock("{firebase-distribution-staging}")
        )
        context.onApplyRule("build.gradle",
            CleanupMarkedLine("{firebase-distribution}")
        )
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("app/build.gradle",
            RemoveMarkedLine("{firebase-distribution}"),
            RemoveMarkedBlock("{firebase-distribution-debug}"),
            RemoveMarkedBlock("{firebase-distribution-staging}")
        )
        context.onApplyRule("build.gradle",
            RemoveMarkedLine("{firebase-distribution}")
        )
        context.onAddVersionCatalogRules(
            RemoveMarkedLine("appdistribution")
        )
    }

    companion object {
        const val ID = "firebase-distribution"
    }

}