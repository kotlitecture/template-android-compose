package kotli.template.android.compose.devops.distribution.googleplay

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine

class GooglePlayDistributionProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://github.com/Triple-T/gradle-play-publisher"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://github.com/Triple-T/gradle-play-publisher?tab=readme-ov-file#quickstart-guide"

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("app/build.gradle",
            CleanupMarkedLine("{google-play-distribution}"),
            CleanupMarkedBlock("{google-play-distribution-config}")
        )
        context.onApplyRule("build.gradle",
            CleanupMarkedLine("{google-play-distribution}")
        )
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("app/build.gradle",
            RemoveMarkedLine("{google-play-distribution}"),
            RemoveMarkedBlock("{google-play-distribution-config}")
        )
        context.onApplyRule("build.gradle",
            RemoveMarkedLine("{google-play-distribution}")
        )
        context.onApplyRule("app/assemble/google-play-publisher.json",
            RemoveFile()
        )
        context.onAddVersionCatalogRules(
            RemoveMarkedLine("googlePlayPublisher")
        )
    }

    companion object {
        const val ID = "google-play-distribution"
    }

}