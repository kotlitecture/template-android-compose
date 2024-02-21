package kotli.template.android.compose.dataflow.storage.room

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.RemoveMarkedLine

class RoomProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
    )

    override fun doApply(context: TemplateContext) {

    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyVersionCatalogRules(RemoveMarkedLine("room"))
    }

    companion object {
        const val ID = "room"
    }

}