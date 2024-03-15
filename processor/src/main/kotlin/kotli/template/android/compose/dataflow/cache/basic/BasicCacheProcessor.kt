package kotli.template.android.compose.dataflow.cache.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class BasicCacheProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("core/data/src/main/kotlin/core/data/datasource/cache", RemoveFile())
        state.onApplyRules("*CacheSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.cache.basic"
    }

}