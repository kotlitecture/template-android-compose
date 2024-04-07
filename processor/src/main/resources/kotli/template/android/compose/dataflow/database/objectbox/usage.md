## Overview

- Component package: `app.datasource.database.objectbox`
- DI integration: `app.di.datasource.ProvidesObjectBoxSource`

The integration includes the following components:

- **AppObjectBoxSource**: Serves as a holder of the ObjectBox Store instance and acts as a service locator for all DAO objects.
- **User** and **UserDao**: An example entity and its associated DAO object. These classes serve as templates that can be used to create your own entities and DAOs.

## Create new Entity and DAO

The official guide for defining entities can be found here: [https://docs.objectbox.io/entity-annotations](https://docs.objectbox.io/entity-annotations)

Imagine you need to add a new entity called **Address**. Here are the steps to follow.

### 1. Create the `Address` entity class

You can use the `User` class in `app.datasource.database.objectbox.entity` as a template.

```kotlin
@Entity
data class Address(
    @Id
    override var id: Long = 0L,
    var country: String? = null,
    var city: String? = null,
    var street: String? = null
) : BoxEntity()
```

### 2. Create the `AddressDao` interface

You can use the `UserDao` interface in `app.datasource.database.objectbox.dao` as a template.

```kotlin
class AddressDao(box: Box<Address>) : BoxDao<Address>(box)
```

### 3. Register `AddressDao` in `AppObjectBoxSource`

```kotlin
class AppObjectBoxSource(
    private val app: Application,
    private val databaseName: String = "db"
) {
    ...
    val addressDao by lazy { AddressDao(store.boxFor(Address::class.java)) }
    ...
}
```

## Usage of DAO in your code

In this example, we will directly use `AppObjectBoxSource` from the `ViewModel` to access `AddressDao`. However, it is recommended to create a separate `Repository` layer and call all data sources from there.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val objectBoxSource: AppObjectBoxSource,
    private val pagingSource: AppPagingSource,
    private val appState: AppState
) : BaseViewModel() {

    val addressesStore = StoreObject<Flow<PagingData<Address>>>()

    override fun doBind() {
        launchAsync("doBind") {
            val addressDao = objectBoxSource.addressDao
            val pager = pagingSource.getPager { addressDao.getAllPaginated() }
            addressesStore.set(pager.flow.cachedIn(viewModelScope))
        }
    }

    fun onCreate(address: Address) {
        launchAsync("onCreate", appState) {
            val addressDao = objectBoxSource.addressDao
            addressDao.createOrUpdate(address)
        }
    }

    fun onDelete(address: Address) {
        launchAsync("onRemove", appState) {
            val addressDao = objectBoxSource.addressDao
            addressDao.delete(address)
        }
    }

}
```

