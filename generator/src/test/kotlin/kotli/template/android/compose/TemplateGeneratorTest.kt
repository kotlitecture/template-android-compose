@file:OptIn(ExperimentalPathApi::class)

package kotli.template.android.compose

import kotli.engine.TemplateContext
import kotli.engine.model.Layer
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
        val app = Layer(
            id = UUID.randomUUID().toString(),
            namespace = "my.app",
            name = "app-android",
            generator = generator
        )
        val output = ByteArrayOutputStream()
        TemplateContext(layer = app).generateAndZip(output)
        Assertions.assertTrue(output.size() > 50000)
    }

    @Test
    fun `compose template without features`() {
        val app = Layer(
            id = UUID.randomUUID().toString(),
            namespace = "my.app",
            name = "app-android",
            generator = generator
        )
        val context = TemplateContext(
            path = buildPath(),
            layer = app
        )
        context.generateAndGradlew("signingReport", "assembleDebug")
    }

    @Test
    fun `compose template with all features`() {
        val app = Layer(
            id = UUID.randomUUID().toString(),
            namespace = "my.app",
            name = "app-android",
            generator = generator,
            features = generator.getAllFeatures()
        )
        val context = TemplateContext(
            path = buildPath(),
            layer = app
        )
//        context.generateAndGradlew("gradlew", "signingReport", "testDebugUnitTest", "assembleDebug")
        context.generateAndGradlew("signingReport", "assembleDebug")
//        context.generateAndGradlew("gradlew", "assembleRelease")
    }

}