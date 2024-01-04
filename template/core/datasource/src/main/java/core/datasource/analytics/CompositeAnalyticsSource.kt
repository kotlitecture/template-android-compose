package core.datasource.analytics

open class CompositeAnalyticsSource(private val sources: List<IAnalyticsSource>) : IAnalyticsSource {

    override fun setUserId(id: String?) {
        sources.forEach { it.setUserId(id) }
    }

    override fun setUserProperty(key: String, value: String?) {
        sources.forEach { it.setUserProperty(key, value) }
    }

    override fun onError(event: String, error: Throwable) {
        sources.forEach { it.onError(event, error) }
    }

    override fun onEvent(event: String, params: Map<String, String>) {
        sources.forEach { it.onEvent(event, params) }
    }

    override fun onScreenView(screenName: String, params: Map<String, String>) {
        sources.forEach { it.onScreenView(screenName, params) }
    }
}