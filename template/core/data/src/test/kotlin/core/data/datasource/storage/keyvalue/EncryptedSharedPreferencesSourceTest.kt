package core.data.datasource.storage.keyvalue

import org.robolectric.RuntimeEnvironment

class EncryptedSharedPreferencesSourceTest : BaseKeyValueSourceTest() {

    override fun create(): KeyValueSource = EncryptedSharedPreferencesSource(RuntimeEnvironment.getApplication())

}