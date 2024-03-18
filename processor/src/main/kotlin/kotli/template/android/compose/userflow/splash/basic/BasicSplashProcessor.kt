package kotli.template.android.compose.userflow.splash.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine

class BasicSplashProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{userflow.splash.basic}")
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/AppActivity.kt",
            CleanupMarkedBlock("{userflow.splash.basic}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{userflow.splash.basic}")
        )
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt",
            RemoveMarkedBlock("{userflow.splash.basic}"),
            RemoveMarkedLine("splashScreen")
        )
        state.onApplyRules(
            "app/src/main/AndroidManifest.xml",
            RemoveMarkedLine("Theme.App.Splash")
        )
        state.onApplyRules(
            "*themes.xml",
            RemoveMarkedBlock("{userflow.splash.basic}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("splashscreen")
            )
        )
    }

    companion object {
        const val ID = "userflow.splash.basic"
    }

}