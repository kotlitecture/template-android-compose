package core.data.datasource.keyvalue

import org.robolectric.RuntimeEnvironment

class SharedPreferencesDataStoreTest : BaseKeyValueSourceTest() {

    override fun create(): KeyValueSource = SharedPreferencesDataStore(RuntimeEnvironment.getApplication())

}