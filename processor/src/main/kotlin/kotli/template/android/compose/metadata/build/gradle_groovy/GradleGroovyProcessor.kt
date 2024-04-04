package kotli.template.android.compose.metadata.build.gradle_groovy

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotlin.time.Duration.Companion.hours

class GradleGroovyProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://gradle.org/"
    override fun getIntegrationUrl(state: TemplateState): String = "https://docs.gradle.org/current/samples/sample_building_kotlin_applications.html"

    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    companion object {
        const val ID = "metadata.build.gradle_groovy"
    }

}