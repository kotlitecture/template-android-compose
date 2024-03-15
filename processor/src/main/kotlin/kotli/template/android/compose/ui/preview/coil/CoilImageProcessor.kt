package kotli.template.android.compose.ui.preview.coil

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.http.okhttp.OkHttpProcessor
import kotli.template.android.compose.unspecified.startup.StartupInitializerProcessor

class CoilImageProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://coil-kt.github.io/coil/"
    override fun getIntegrationUrl(state: TemplateState): String = "https://coil-kt.github.io/coil/getting_started/"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        StartupInitializerProcessor::class.java,
        OkHttpProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{ui.preview.coil}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                CleanupMarkedLine("coil")
            )
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/AppStartupInitializer.kt",
            RemoveMarkedLine("CoiIImageLoaderInitializer")
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/component/image",
            RemoveFile()
        )
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{ui.preview.coil}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("coil")
            )
        )
    }

    companion object {
        const val ID = "ui.preview.coil"
    }

}