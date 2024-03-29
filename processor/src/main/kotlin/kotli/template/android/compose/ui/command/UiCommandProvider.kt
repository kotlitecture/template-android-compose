package kotli.template.android.compose.ui.command

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.ui.command.basic.BasicCommandProcessor

class UiCommandProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.command"
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.UI
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicCommandProcessor()
    )

}