package app.feature.template

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import core.ui.AppViewModel
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    val app:Application
) : AppViewModel() {

}
