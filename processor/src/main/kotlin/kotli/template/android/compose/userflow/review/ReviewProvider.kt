package kotli.template.android.compose.userflow.review

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider
import kotli.template.android.compose.userflow.review.google.GoogleReviewProcessor

class ReviewProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.review"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GoogleReviewProcessor()
    )

}