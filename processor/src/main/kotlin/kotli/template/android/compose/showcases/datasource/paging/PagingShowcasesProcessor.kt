package kotli.template.android.compose.showcases.datasource.paging

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.ShowcasesRules

object PagingShowcasesProcessor : BaseFeatureProcessor() {

    const val ID = "showcases.datasource.paging"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            ShowcasesRules(
                RemoveMarkedLine("Paging")
            )
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/showcases/datasource/paging",
            RemoveFile()
        )
    }

}