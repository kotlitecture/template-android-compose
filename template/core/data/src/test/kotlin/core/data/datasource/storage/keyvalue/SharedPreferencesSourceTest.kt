package core.data.datasource.storage.keyvalue

import org.robolectric.RuntimeEnvironment

class SharedPreferencesSourceTest : BaseKeyValueSourceTest() {

    override fun create(): KeyValueSource = SharedPreferencesSource(RuntimeEnvironment.getApplication())

}