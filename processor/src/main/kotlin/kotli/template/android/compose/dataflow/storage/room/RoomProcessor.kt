package kotli.template.android.compose.dataflow.storage.room

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine

class RoomProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf()

    override fun doApply(state: TemplateState) {

    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("androidxRoom")))
    }

    companion object {
        const val ID = "dataflow.room"
    }

}