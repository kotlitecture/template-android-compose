package kotli.template.android.compose.dataflow.http

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine

class HttpProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRules("app/src/main/kotlin/app/App.kt",
            CleanupMarkedLine("{httpSource-import}"),
            CleanupMarkedBlock("{httpSource-inject}"),
            CleanupMarkedBlock("{httpSource-client}")
        )
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("app/src/main/kotlin/di/ProvidesHttp.kt", RemoveFile())
        context.onApplyRules("app/src/main/kotlin/app/App.kt",
            RemoveMarkedLine("{httpSource-import}"),
            RemoveMarkedBlock("{httpSource-inject}"),
            RemoveMarkedBlock("{httpSource-client}")
        )
    }

    companion object {
        const val ID = "http"
    }

}