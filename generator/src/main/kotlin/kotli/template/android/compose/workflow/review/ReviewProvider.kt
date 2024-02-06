package kotli.template.android.compose.workflow.review

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.workflow.review.market.GooglePlayReviewProcessor

class ReviewProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): IFeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        GooglePlayReviewProcessor()
    )

    companion object {
        const val ID = "review"
    }
}