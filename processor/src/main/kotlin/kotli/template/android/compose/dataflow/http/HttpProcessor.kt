package kotli.template.android.compose.dataflow.http

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile

class HttpProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/src/main/kotlin/app/App.kt",
            CleanupMarkedLine("{httpSource-import}"),
            CleanupMarkedBlock("{httpSource-inject}"),
            CleanupMarkedBlock("{httpSource-client}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*HttpSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.http"
    }

}