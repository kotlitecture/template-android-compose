package kotli.template.android.compose.essentials.build

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.essentials.EssentialsProvider
import kotli.template.android.compose.essentials.build.gradle_groovy.GradleGroovyProcessor

class BuildToolProvider : EssentialsProvider() {

    override fun getId(): String = "metadata.build"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GradleGroovyProcessor()
    )

}