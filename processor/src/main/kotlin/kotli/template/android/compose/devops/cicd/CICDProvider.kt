package kotli.template.android.compose.devops.cicd

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.devops.cicd.github.GithubProcessor

object CICDProvider : BaseFeatureProvider() {

    override fun getId(): String = "devops.cicd"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.DevOps

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GithubProcessor
    )

}