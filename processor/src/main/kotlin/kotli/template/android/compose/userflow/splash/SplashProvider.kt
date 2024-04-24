package kotli.template.android.compose.userflow.splash

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider
import kotli.template.android.compose.userflow.splash.basic.BasicSplashProcessor

class SplashProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.splash"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicSplashProcessor
    )

}