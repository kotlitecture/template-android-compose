package kotli.template.android.compose.testing.unit_testing.robolectric

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.hours

class RobolectricTestingProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://robolectric.org/"
    override fun getIntegrationUrl(state: TemplateState): String = "https://robolectric.org/getting-started/"
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "*.gradle",
            CleanupMarkedLine("{testing.unit_testing.robolectric}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "core/testing",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/test",
            RemoveFile()
        )
        state.onApplyRules(
            "*.gradle",
            RemoveMarkedLine("{testing.unit_testing.robolectric}")
        )
        state.onApplyRules(VersionCatalogRules(
            RemoveMarkedLine("robolectric")
        ))
    }

    companion object {
        const val ID = "testing.unit_testing.robolectric"
    }

}