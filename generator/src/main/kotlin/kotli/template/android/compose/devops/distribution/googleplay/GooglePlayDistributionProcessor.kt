package kotli.template.android.compose.devops.distribution.googleplay

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
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
        context.onApplyRules("app/build.gradle",
            CleanupMarkedLine("{google-play-distribution}"),
            CleanupMarkedBlock("{google-play-distribution-config}")
        )
        context.onApplyRules("build.gradle",
            CleanupMarkedLine("{google-play-distribution}")
        )
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("app/build.gradle",
            RemoveMarkedLine("{google-play-distribution}"),
            RemoveMarkedBlock("{google-play-distribution-config}")
        )
        context.onApplyRules("build.gradle",
            RemoveMarkedLine("{google-play-distribution}")
        )
        context.onApplyRules("app/assemble/google-play-publisher.json",
            RemoveFile()
        )
        context.onApplyVersionCatalogRules(
            RemoveMarkedLine("googlePlayPublisher")
        )
    }

    companion object {
        const val ID = "google-play-distribution"
    }

}