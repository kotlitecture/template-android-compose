package di

import androidx.fragment.app.FragmentActivity
import core.datasource.market.review.MarketReviewSource
import core.datasource.review.IReviewSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal class ProvidesReviewSource {

    @Provides
    fun source(
        activity: FragmentActivity
    ): IReviewSource {
        return MarketReviewSource(activity)
    }

}