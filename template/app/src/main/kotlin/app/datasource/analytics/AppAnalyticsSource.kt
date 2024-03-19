package app.datasource.analytics

import app.datasource.analytics.firebase.FirebaseAnalyticsSource
import core.data.datasource.analytics.CompositeAnalyticsSource

/**
 * Decorator class for working with analytics events.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppAnalyticsSource : CompositeAnalyticsSource(
    listOf(
        FirebaseAnalyticsSource()
    )
)