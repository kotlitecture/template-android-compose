package kotli.template.android.compose.essentials.toolkit.compose

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotlin.time.Duration.Companion.hours

class ComposeProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/develop/ui/compose"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/develop/ui/compose/documentation"
    override fun getIntegrationEstimate(state: TemplateState): Long = 8.hours.inWholeMilliseconds

    companion object {
        const val ID = "metadata.toolkit.compose"
    }

}