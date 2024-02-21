@file:OptIn(ExperimentalPathApi::class)

package kotli.template.android.compose

import kotli.engine.DefaultTemplateRegistry
import kotli.engine.model.Layer
import kotli.flow.FileOutputFlow
import kotli.flow.GradleExecutionFlow
import kotli.flow.ZipOutputFlow
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
        val flow = FileOutputFlow(
            layer = Layer(
                id = UUID.randomUUID().toString(),
                generatorId = generator.getId(),
                namespace = "my.app",
                name = "app-android",
            ),
            registry = registry,
        )
        ZipOutputFlow(flow, output).proceed()
        Assertions.assertTrue(output.size() > 50000)
    }

    @Test
    fun `compose template without features`() {
        val flow = FileOutputFlow(
            layer = Layer(
                id = UUID.randomUUID().toString(),
                generatorId = generator.getId(),
                namespace = "my.app",
                name = "app-android",
            ),
            layerPath = buildPath(),
            registry = registry
        )
        GradleExecutionFlow(flow, arrayOf("signingReport", "assembleDebug")).proceed()
    }

    @Test
    fun `compose template with all features`() {
        val flow = FileOutputFlow(
            layer = Layer(
                id = UUID.randomUUID().toString(),
                generatorId = generator.getId(),
                namespace = "my.app",
                name = "app-android",
            ),
            layerPath = buildPath(),
            registry = registry,
            fatLayer = true
        )
        GradleExecutionFlow(flow, arrayOf("signingReport", "assembleDebug")).proceed()
//        GradleExecutionFlow(flow, arrayOf("signingReport", "assembleRelease")).proceed()
//        GradleExecutionFlow(flow, arrayOf("signingReport", "testDebugUnitTest", "assembleDebug")).proceed()
    }

}