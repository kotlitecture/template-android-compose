package kotli.template.android.compose.userflow.splash.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.AppActivityRules
import kotlin.time.Duration.Companion.hours

object BasicSplashProcessor : BaseFeatureProcessor() {

    const val ID = "userflow.splash.basic"

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun getWebUrl(state: TemplateState): String =
        "https://developer.android.com/develop/ui/views/launch/splash-screen"

    override fun getIntegrationUrl(state: TemplateState): String =
        "https://developer.android.com/reference/kotlin/androidx/core/splashscreen/SplashScreen"

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{userflow.splash.basic}")
        )
        state.onApplyRules(
            AppActivityRules(
                CleanupMarkedBlock("{userflow.splash.basic}")
            )
        )
        state.onApplyRules(
            "*themes.xml",
            CleanupMarkedBlock("{userflow.splash.basic}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{userflow.splash.basic}")
        )
        state.onApplyRules(
            AppActivityRules(
                RemoveMarkedBlock("{userflow.splash.basic}"),
                RemoveMarkedLine("splashScreen")
            )
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

}