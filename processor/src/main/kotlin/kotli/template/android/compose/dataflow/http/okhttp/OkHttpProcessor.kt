package kotli.template.android.compose.dataflow.http.okhttp

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class OkHttpProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*HttpSource*", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("okhttp")))
    }

    companion object {
        const val ID = "dataflow.http.okhttp"
    }

}