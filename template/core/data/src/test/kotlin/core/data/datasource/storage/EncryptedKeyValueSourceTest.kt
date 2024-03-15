package core.data.datasource.storage

import core.data.datasource.storage.keyvalue.AndroidEncryptedKeyValueSource
import core.data.datasource.storage.keyvalue.KeyValueSource
import org.robolectric.RuntimeEnvironment

class EncryptedKeyValueSourceTest : BaseKeyValueSourceTest() {

    override fun provider(): KeyValueSource = AndroidEncryptedKeyValueSource(RuntimeEnvironment.getApplication())

}