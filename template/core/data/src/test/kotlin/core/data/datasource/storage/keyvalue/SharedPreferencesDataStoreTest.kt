package core.data.datasource.storage.keyvalue

import org.robolectric.RuntimeEnvironment

class SharedPreferencesDataStoreTest : BaseKeyValueSourceTest() {

    override fun create(): KeyValueSource = SharedPreferencesDataStore(RuntimeEnvironment.getApplication())

}