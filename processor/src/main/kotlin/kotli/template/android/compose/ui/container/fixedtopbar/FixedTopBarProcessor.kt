package kotli.template.android.compose.ui.container.fixedtopbar

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.template.android.compose.ui.container.fixedheaderfooter.FixedHeaderFooterProcessor
import kotlin.time.Duration.Companion.hours

class FixedTopBarProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        FixedHeaderFooterProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/container/FixedTopBarLayout.kt",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "ui.container.fixedtopbar"
    }

}