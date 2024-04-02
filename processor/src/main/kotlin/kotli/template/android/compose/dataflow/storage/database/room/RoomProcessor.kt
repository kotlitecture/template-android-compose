package kotli.template.android.compose.dataflow.storage.database.room

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine

object RoomProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.storage.database.room"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf()

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("androidxRoom")))
    }

}