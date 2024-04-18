package kotli.template.android.compose.ui.component.coil

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.AppStartupInitializerRules
import kotli.template.android.compose.dataflow.http.okhttp.OkHttpProcessor
import kotli.template.android.compose.unspecified.startup.StartupInitializerProcessor
import kotlin.time.Duration.Companion.minutes

class CoilImageProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://coil-kt.github.io/coil/"
    override fun getIntegrationUrl(state: TemplateState): String =
        "https://coil-kt.github.io/coil/getting_started/"

    override fun getIntegrationEstimate(state: TemplateState): Long = 10.minutes.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        StartupInitializerProcessor::class.java,
        OkHttpProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{ui.component.coil}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            AppStartupInitializerRules(
                RemoveMarkedLine("CoiIImageLoaderInitializer")
            )
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/AppInitializerEntryPoint.kt",
            RemoveMarkedLine("CoiIImageLoaderInitializer")
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/component/coil",
            RemoveFile()
        )
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{ui.component.coil}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("coil")
            )
        )
    }

    companion object {
        const val ID = "ui.component.coil"
    }

}