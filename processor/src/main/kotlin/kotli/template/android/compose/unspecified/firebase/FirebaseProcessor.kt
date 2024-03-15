package kotli.template.android.compose.unspecified.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.unspecified.googleservices.GoogleServicesProcessor

class FirebaseProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{firebase}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("firebaseBom")
            )
        )
    }

    companion object {
        const val ID = "firebase"
    }

}