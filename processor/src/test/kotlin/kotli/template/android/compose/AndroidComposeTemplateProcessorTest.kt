@file:OptIn(ExperimentalPathApi::class)

package kotli.template.android.compose

import kotli.engine.DefaultTemplateRegistry
import kotli.engine.generator.GradleProjectGenerator
import kotli.engine.generator.PathOutputGenerator
import kotli.engine.generator.ZipOutputGenerator
import kotli.engine.model.Feature
import kotli.engine.model.Layer
import kotli.template.android.compose.dataflow.storage.keyvalue.datastore.DataStoreProcessor
import kotli.template.android.compose.metadata.design.material3.Material3Processor
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Path
import java.util.Random
import java.util.UUID
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively
import kotlin.test.Test

class AndroidComposeTemplateProcessorTest {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val processor = AndroidComposeTemplateProcessor()
    private val registry = DefaultTemplateRegistry(processor)

    private fun buildPath(): Path {
        return File("build/template").toPath().toAbsolutePath().also { it.deleteRecursively() }
    }

    @Test
    fun `icon, title and description exist`() {
        Assertions.assertNotNull(processor.getIcon())
        Assertions.assertNotNull(processor.getTitle())
        Assertions.assertNotNull(processor.getDescription())
    }

    @Test
    fun `features have title and description`() {
        processor.getFeatureProviders()
            .filter { provider -> provider.getProcessors().any { !it.isInternal() } }
            .forEach { provider ->
                Assertions.assertNotNull(processor.getTitle())
                provider.getProcessors()
                    .filter { !it.isInternal() }
                    .forEach { processor ->
                        Assertions.assertNotNull(processor.getTitle())
                        Assertions.assertNotNull(processor.getDescription())
                    }
            }
    }

    @Test
    fun `feature providers are unique`() {
        val providers = processor.getFeatureProviders()
        val notUnique = providers.groupBy { it.getId() }.filter { it.value.size > 1 }
        notUnique.forEach { group ->
            Assertions.fail(group.value.toString())
        }
    }

    @Test
    fun `feature processors are unique`() {
        val processors = processor.getFeatureProviders().map { it.getProcessors() }.flatten()
        val notUnique = processors.groupBy { it.getId() }.filter { it.value.size > 1 }
        notUnique.forEach { group ->
            Assertions.fail(group.value.toString())
        }
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
                features = listOf(
                    Feature(DataStoreProcessor.ID),
                    Feature(Material3Processor.ID)
                )
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

    @Test
    fun `compose template with random features`() {
        runBlocking {
            val processors = processor.getFeatureProviders()
                .map { it.getProcessors() }
                .flatten()
                .filter { !it.isInternal() }
            val features = mutableSetOf<Feature>()
            repeat(Random().nextInt(1, processors.size + 1)) {
                features.add(Feature(processors.random().getId()))
            }
            logger.debug("features :: {} -> {}", features.size, features.map { it.id })
            val layer = Layer(
                id = UUID.randomUUID().toString(),
                processorId = processor.getId(),
                features = features.toList(),
                namespace = "my.app",
                name = "app-android",
            )
            val generator = PathOutputGenerator(buildPath(), registry)
            val gradleGenerator = GradleProjectGenerator(arrayOf("signingReport", "assembleDebug"), generator)
            gradleGenerator.generate(layer)
        }
    }

}