package kotli.template.android.compose.userflow.loader

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider
import kotli.template.android.compose.userflow.loader.data.DataLoaderProcessor

class LoaderProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.loader"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        DataLoaderProcessor()
    )

}