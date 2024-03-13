package app.datasource.analytics

import app.datasource.analytics.firebase.FirebaseAnalyticsSource
import core.dataflow.datasource.analytics.CompositeAnalyticsSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppAnalyticsSource @Inject constructor() : CompositeAnalyticsSource(
    listOf(
        FirebaseAnalyticsSource() // {firebase-analytics}
    )
)