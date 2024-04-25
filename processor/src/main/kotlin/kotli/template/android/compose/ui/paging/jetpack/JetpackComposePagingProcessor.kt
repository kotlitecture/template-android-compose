package kotli.template.android.compose.ui.paging.jetpack

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.AndroidStringsRules
import kotli.template.android.compose.dataflow.paging.jetpack.JetpackPagingProcessor
import kotli.template.android.compose.showcases.datasource.paging.PagingShowcasesProcessor
import kotlin.time.Duration.Companion.minutes

object JetpackComposePagingProcessor : BaseFeatureProcessor() {

    const val ID = "ui.paging.jetpack"

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/topic/libraries/architecture/paging/v3-overview"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/topic/libraries/architecture/paging/v3-overview#ui"
    override fun getIntegrationEstimate(state: TemplateState): Long = 15.minutes.inWholeMilliseconds
    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        JetpackPagingProcessor::class.java,
        PagingShowcasesProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/paging",
            RemoveFile()
        )
        state.onApplyRules(
            AndroidStringsRules(
                RemoveMarkedLine("data_not_found", singleLine = true)
            )
        )
    }

}