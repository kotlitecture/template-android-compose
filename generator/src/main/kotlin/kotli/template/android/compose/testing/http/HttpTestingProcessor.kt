package kotli.template.android.compose.testing.http

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.dataflow.http.HttpProcessor

class HttpTestingProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        HttpProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.onApplyRules("settings.gradle", CleanupMarkedLine("{testing-http}"))
        context.onApplyRules("app/build.gradle", CleanupMarkedLine("{testing-http}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("settings.gradle", RemoveMarkedLine("{testing-http}"))
        context.onApplyRules("app/build.gradle", RemoveMarkedLine("{testing-http}"))
        context.onApplyRules("core/testing-http", RemoveFile())
        context.onApplyVersionCatalogRules(RemoveMarkedLine("chucker"))
    }

    companion object {
        const val ID = "testing-http"
    }

}