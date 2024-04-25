package kotli.template.android.compose.showcases.datasource.http

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.ShowcasesRules

object HttpShowcasesProcessor : BaseFeatureProcessor() {

    const val ID = "showcases.datasource.http"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            ShowcasesRules(
                RemoveMarkedLine("Http")
            )
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/showcases/datasource/http",
            RemoveFile()
        )
    }

}