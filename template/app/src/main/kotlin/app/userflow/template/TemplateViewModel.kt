package app.userflow.template

import core.ui.AppViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val navigationState: NavigationState
) : AppViewModel() {

    private val counter = AtomicInteger(0)

    fun onBack() {
        navigationState.onBack()
    }

    fun onTop() {
        val data = TemplateDestination.Data(counter.incrementAndGet().toString())
        navigationState.onNext(TemplateDestination, data)
    }

    fun onBottom() {
        val data = TemplateDestination.Data(counter.incrementAndGet().toString())
        navigationState.onNext(TemplateDestination, data)
    }

}
