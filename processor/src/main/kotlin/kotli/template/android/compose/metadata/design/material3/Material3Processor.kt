package kotli.template.android.compose.metadata.design.material3

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotlin.time.Duration.Companion.hours

class Material3Processor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://m3.material.io/"
    override fun getIntegrationUrl(state: TemplateState): String = "https://m3.material.io/develop"
    override fun getIntegrationEstimate(state: TemplateState): Long = 4.hours.inWholeMilliseconds

    companion object {
        const val ID = "metadata.design.material3"
    }

}