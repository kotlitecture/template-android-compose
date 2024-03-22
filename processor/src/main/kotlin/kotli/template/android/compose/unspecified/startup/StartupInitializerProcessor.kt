package kotli.template.android.compose.unspecified.startup

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.hours

class StartupInitializerProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun getConfiguration(state: TemplateState): String? {
        return super.getConfiguration(state)?.replace("kotli.app", state.layer.namespace)
    }

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{startup-initializer}", true))
        state.onApplyRules("app/src/main/AndroidManifest.xml", CleanupMarkedBlock("{startup-initializer}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{startup-initializer}", true))
        state.onApplyRules("app/src/main/AndroidManifest.xml", RemoveMarkedBlock("{startup-initializer}"))
        state.onApplyRules("app/src/main/kotlin/app/AppStartupInitializer.kt", RemoveFile())
        state.onApplyRules("app/src/main/kotlin/app/startup", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("appStartup")))
    }

    companion object {
        const val ID = "startup-initializer"
    }

}