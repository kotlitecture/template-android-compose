## Overview

- Component package: `app.ui.paging`

## Example

This example utilizes `AppRoomSource` to retrieve data from a Room database and load it using a `Pager` configured with the assistance of `AppPagingSource`.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val pagingSource: AppPagingSource,
    private val roomSource: AppRoomSource
) : BaseViewModel() {

    val usersStore = StoreObject<Flow<PagingData<User>>>()

    override fun doBind() {
        launchAsync("doBind") {
            val userDao = roomSource.userDao
            val pager = pagingSource.getPager { userDao.getAllPaginated() }
            usersStore.set(pager.flow.cachedIn(viewModelScope))
        }
    }

}

@Composable
fun TemplateScreen() {
    val viewModel: TemplateViewModel = appViewModel()
    val users = viewModel.usersStore.asStateValue()
    val items = users?.collectAsLazyPagingItems()
    FixedTopBarLazyColumnLayout {
        SimpleLazyPagingList(
            items = items,
            itemContent = { UserItem(it) }
        )
    }
}

@Composable
private fun UserItem(user: User?) {
    Text(text = user?.firstName.orEmpty())
}
```