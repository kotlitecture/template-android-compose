package kotli.template.android.compose.essentials.di.hilt

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotlin.time.Duration.Companion.hours

class HiltProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://dagger.dev/hilt/"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/training/dependency-injection/hilt-android"
    override fun getIntegrationEstimate(state: TemplateState): Long = 4.hours.inWholeMilliseconds

    companion object {
        const val ID = "metadata.di.hilt"
    }

}