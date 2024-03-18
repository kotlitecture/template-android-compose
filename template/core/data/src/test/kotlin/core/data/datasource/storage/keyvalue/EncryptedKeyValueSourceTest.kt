package core.data.datasource.storage.keyvalue

import org.robolectric.RuntimeEnvironment

class EncryptedKeyValueSourceTest : BaseKeyValueSourceTest() {

    override fun provider(): KeyValueSource = BasicEncryptedKeyValueSource(RuntimeEnvironment.getApplication())

}