@file:OptIn(ExperimentalPathApi::class)

package kotli.template.android.compose

import kotli.engine.DefaultTemplateRegistry
import kotli.engine.generator.GradleProjectGenerator
import kotli.engine.generator.PathOutputGenerator
import kotli.engine.generator.ZipOutputGenerator
import kotli.engine.model.Feature
import kotli.engine.model.Layer
import kotli.template.android.compose.userflow.loader.data.DataLoaderProcessor
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Path
import java.util.UUID
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively
import kotlin.test.Test

class AndroidComposeTemplateProcessorTest {

    private val processor = AndroidComposeTemplateProcessor()
    private val registry = DefaultTemplateRegistry(processor)

    private fun buildPath(): Path {
        return File("build/template").toPath().toAbsolutePath().also { it.deleteRecursively() }
    }

    @Test
    fun `icon and title exist`() {
        Assertions.assertNotNull(processor.getIcon())
        Assertions.assertNotNull(processor.getTitle())
        Assertions.assertNotNull(processor.getDescription())
    }

    @Test
    fun `compose template in memory`() {
        runBlocking {
            val output = ByteArrayOutputStream()
            val layer = Layer(
                id = UUID.randomUUID().toString(),
                processorId = processor.getId(),
                namespace = "my.app",
                name = "app-android",
            )
            val generator = PathOutputGenerator(registry = registry)
            val zipGenerator = ZipOutputGenerator(output, generator)
            zipGenerator.generate(layer)
            Assertions.assertTrue(output.size() > 50000)
        }
    }

    @Test
    fun `compose template without features`() {
        runBlocking {
            val layer = Layer(
                id = UUID.randomUUID().toString(),
                processorId = processor.getId(),
                namespace = "my.app",
                name = "app-android",
                features = listOf(Feature(DataLoaderProcessor.ID))
            )
            val generator = PathOutputGenerator(buildPath(), registry)
            val gradleGenerator = GradleProjectGenerator(arrayOf("signingReport", "assembleDebug"), generator)
            gradleGenerator.generate(layer)
        }
    }

    @Test
    fun `compose template with all features`() {
        runBlocking {
            val layer = Layer(
                id = UUID.randomUUID().toString(),
                processorId = processor.getId(),
                namespace = "my.app",
                name = "app-android",
            )
            val generator = PathOutputGenerator(buildPath(), registry, fat = true)
            val gradleGenerator = GradleProjectGenerator(arrayOf("signingReport", "assembleDebug"), generator)
            gradleGenerator.generate(layer)
        }
    }

}