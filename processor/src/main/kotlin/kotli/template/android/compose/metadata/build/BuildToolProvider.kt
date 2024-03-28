package kotli.template.android.compose.metadata.build

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.metadata.MetadataProvider
import kotli.template.android.compose.metadata.build.gradle_groovy.GradleGroovyProcessor

class BuildToolProvider : MetadataProvider() {

    override fun getId(): String = "metadata.build"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GradleGroovyProcessor()
    )

}