@file:OptIn(ExperimentalPathApi::class)

package kotli.template.android.compose

import kotli.engine.DefaultTemplateRegistry
import kotli.engine.extensions.getAllFeatures
import kotli.engine.model.Layer
import kotli.flow.DefaultTemplateFlow
import kotli.flow.GradleCmdTemplateFlow
import kotli.flow.ZipTemplateFlow
import org.junit.jupiter.api.Assertions
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Path
import java.util.UUID
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively
import kotlin.test.Test

class TemplateGeneratorTest {

    private val generator = TemplateGenerator()

    private val registry = DefaultTemplateRegistry(listOf(generator))

    private fun buildPath(): Path {
        return File("build/template").toPath().toAbsolutePath().also { it.deleteRecursively() }
    }

    @Test
    fun `icon and title exist`() {
        Assertions.assertNotNull(generator.getIcon())
        Assertions.assertNotNull(generator.getTitle())
        Assertions.assertNotNull(generator.getDescription())
    }

    @Test
    fun `compose template in memory`() {
        val output = ByteArrayOutputStream()
        val flow = DefaultTemplateFlow(
            layer = Layer(
                id = UUID.randomUUID().toString(),
                generatorId = generator.getId(),
                namespace = "my.app",
                name = "app-android",
            ),
            registry = registry,
        )
        ZipTemplateFlow(flow, output).proceed()
        Assertions.assertTrue(output.size() > 50000)
    }

    @Test
    fun `compose template without features`() {
        val flow = DefaultTemplateFlow(
            layer = Layer(
                id = UUID.randomUUID().toString(),
                generatorId = generator.getId(),
                namespace = "my.app",
                name = "app-android",
            ),
            layerPath = buildPath(),
            registry = registry
        )
        GradleCmdTemplateFlow(flow, arrayOf("signingReport", "assembleDebug")).proceed()
    }

    @Test
    fun `compose template with all features`() {
        val flow = DefaultTemplateFlow(
            layer = Layer(
                features = generator.getAllFeatures(),
                id = UUID.randomUUID().toString(),
                generatorId = generator.getId(),
                namespace = "my.app",
                name = "app-android",
            ),
            layerPath = buildPath(),
            registry = registry,
        )
        GradleCmdTemplateFlow(flow, arrayOf("signingReport", "assembleDebug")).proceed()
//        GradleCmdTemplateFlow(flow, arrayOf("signingReport", "assembleRelease")).proceed()
//        GradleCmdTemplateFlow(flow, arrayOf("signingReport", "testDebugUnitTest", "assembleDebug")).proceed()
    }

}