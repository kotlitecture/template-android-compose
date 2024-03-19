package app.userflow.template

import core.data.datasource.analytics.AnalyticsSource
import core.ui.AppViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val navigationState: NavigationState,
    private val analyticsSource: AnalyticsSource // AppAnalyticsSource,
) : AppViewModel() {

    private val counter = AtomicInteger(0)

    fun onBack() {
        navigationState.onBack()
    }

    fun onTop() {
        val data = TemplateDestination.Data(counter.incrementAndGet().toString())
        navigationState.onNavigate(TemplateDestination, data)
        analyticsSource.onEvent("click_top")
    }

    fun onBottom() {
        val data = TemplateDestination.Data(counter.incrementAndGet().toString())
        navigationState.onNavigate(TemplateDestination, data)
        analyticsSource.onEvent("click_bottom", mapOf("counter" to data.title))
    }

}
