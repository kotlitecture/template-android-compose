package kotli.template.android.compose.userflow.review

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.userflow.review.market.GooglePlayReviewProcessor

class ReviewProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): FeatureType = FeatureTypes.UserFlow

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GooglePlayReviewProcessor()
    )

    companion object {
        const val ID = "review"
    }
}