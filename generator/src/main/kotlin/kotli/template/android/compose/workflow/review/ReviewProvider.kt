package kotli.template.android.compose.workflow.review

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.workflow.review.market.GooglePlayReviewProcessor

class ReviewProvider : FeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        GooglePlayReviewProcessor()
    )

    companion object {
        const val ID = "review"
    }
}