package kotli.template.android.compose.userflow.internet.no

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.network.basic.BasicNetworkProcessor
import kotlin.time.Duration.Companion.hours

class NoInternetProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        BasicNetworkProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*NoInternet*", RemoveFile())
        state.onApplyRules("*strings.xml", RemoveMarkedLine("internet_error"))
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("NoInternetProvider"))
    }

    companion object {
        const val ID = "userflow.internet.no"
    }

}