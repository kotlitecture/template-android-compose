package kotli.template.android.compose.metadata.navigation.jetpack

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotlin.time.Duration.Companion.hours

class JetpackProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/guide/navigation"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/guide/navigation#set-up"
    override fun getIntegrationEstimate(state: TemplateState): Long = 4.hours.inWholeMilliseconds

    companion object {
        const val ID = "metadata.navigation.jetpack"
    }

}