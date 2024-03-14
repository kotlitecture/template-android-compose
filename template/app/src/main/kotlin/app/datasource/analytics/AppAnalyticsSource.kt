package app.datasource.analytics

import app.datasource.analytics.firebase.FirebaseAnalyticsSource
import core.data.datasource.analytics.CompositeAnalyticsSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppAnalyticsSource @Inject constructor() : CompositeAnalyticsSource(
    listOf(
        FirebaseAnalyticsSource()
    )
)