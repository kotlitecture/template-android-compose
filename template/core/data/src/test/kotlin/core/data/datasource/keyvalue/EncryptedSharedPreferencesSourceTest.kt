package core.data.datasource.keyvalue

import org.robolectric.RuntimeEnvironment

class EncryptedSharedPreferencesSourceTest : BaseKeyValueSourceTest() {

    override fun create(): KeyValueSource = EncryptedSharedPreferencesSource(RuntimeEnvironment.getApplication())

}