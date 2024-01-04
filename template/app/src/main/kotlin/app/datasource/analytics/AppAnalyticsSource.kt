package app.datasource.analytics

import core.datasource.analytics.CompositeAnalyticsSource
import core.datasource.firebase.analytics.FirebaseAnalyticsSource // {firebase-analytics}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppAnalyticsSource @Inject constructor() : CompositeAnalyticsSource(
    listOf(
        FirebaseAnalyticsSource() // {firebase-analytics}
    )
)