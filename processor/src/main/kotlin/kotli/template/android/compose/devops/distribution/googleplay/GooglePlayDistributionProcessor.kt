package kotli.template.android.compose.devops.distribution.googleplay

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine

class GooglePlayDistributionProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://github.com/Triple-T/gradle-play-publisher"
    override fun getIntegrationUrl(state: TemplateState): String = "https://github.com/Triple-T/gradle-play-publisher?tab=readme-ov-file#quickstart-guide"

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle",
            CleanupMarkedLine("{google-play-distribution}"),
            CleanupMarkedBlock("{google-play-distribution-config}")
        )
        state.onApplyRules("build.gradle",
            CleanupMarkedLine("{google-play-distribution}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle",
            RemoveMarkedLine("{google-play-distribution}"),
            RemoveMarkedBlock("{google-play-distribution-config}")
        )
        state.onApplyRules("build.gradle",
            RemoveMarkedLine("{google-play-distribution}")
        )
        state.onApplyRules("app/assemble/google-play-publisher.json",
            RemoveFile()
        )
        state.onApplyRules(
            VersionCatalogRules(RemoveMarkedLine("googlePlayPublisher"))
        )
    }

    companion object {
        const val ID = "google-play-distribution"
    }

}