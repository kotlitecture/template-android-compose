package kotli.template.android.compose.userflow.review

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.userflow.review.market.GooglePlayReviewProcessor

class ReviewProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): IFeatureType = FeatureType.UserFlow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        GooglePlayReviewProcessor()
    )

    companion object {
        const val ID = "review"
    }
}