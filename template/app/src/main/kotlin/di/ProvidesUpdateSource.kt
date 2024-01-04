package di

import androidx.fragment.app.FragmentActivity
import core.datasource.market.update.MarketUpdateSource
import core.datasource.update.IUpdateSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal class ProvidesUpdateSource {

    @Provides
    fun source(
        activity: FragmentActivity
    ): IUpdateSource {
        return MarketUpdateSource(activity)
    }

}