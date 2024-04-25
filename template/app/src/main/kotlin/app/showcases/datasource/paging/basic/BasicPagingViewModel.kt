package app.showcases.datasource.paging.basic

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.datasource.paging.AppPagingSource
import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasicPagingViewModel @Inject constructor(
    private val navigationState: NavigationState,
    private val pagingSource: AppPagingSource
) : BaseViewModel() {

    val itemsFlow by lazy {
        val pager = pagingSource.getPager { BasicPagingSource() }
        pager.flow.cachedIn(viewModelScope)
    }

    fun onBack() {
        navigationState.onBack()
    }

}
