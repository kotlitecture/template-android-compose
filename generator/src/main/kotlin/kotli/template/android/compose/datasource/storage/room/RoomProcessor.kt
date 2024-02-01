package kotli.template.android.compose.datasource.storage.room

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.IFeatureProcessor
import kotli.engine.TemplateContext

class RoomProcessor : AbstractFeatureProcessor() {

    override val id: String = ID

    override fun dependencies(): List<Class<out IFeatureProcessor>> = listOf(
    )

    override fun doApply(context: TemplateContext) {

    }

    override fun doRemove(context: TemplateContext) {
        context.applyVersionCatalog {
            removeLine("room")
        }
    }

    companion object {
        const val ID = "room"
    }

}