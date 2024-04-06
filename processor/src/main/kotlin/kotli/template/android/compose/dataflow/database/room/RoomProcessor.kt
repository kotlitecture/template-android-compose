package kotli.template.android.compose.dataflow.database.room

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.hours

object RoomProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.storage.database.room"

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/training/data-storage/room"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/training/data-storage/room#setup"
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "build.gradle",
            CleanupMarkedLine("{dataflow.storage.database.room}")
        )
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{dataflow.storage.database.room}"),
            CleanupMarkedBlock("{dataflow.storage.database.room.config}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "build.gradle",
            RemoveMarkedLine("{dataflow.storage.database.room}")
        )
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{dataflow.storage.database.room}"),
            RemoveMarkedBlock("{dataflow.storage.database.room.config}")
        )
        state.onApplyRules(
            VersionCatalogRules(RemoveMarkedLine("androidxRoom"))
        )
    }

}