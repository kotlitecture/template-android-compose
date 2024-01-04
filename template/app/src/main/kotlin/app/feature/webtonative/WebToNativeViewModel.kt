package app.feature.webtonative

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import core.ui.mvvm.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class WebToNativeViewModel @Inject constructor(
    val app:Application
) : BaseViewModel() {

}
