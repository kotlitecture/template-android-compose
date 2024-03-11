package core.dataflow.datasource.storage

import org.robolectric.RuntimeEnvironment

class EncryptedKeyValueSourceTest : BaseKeyValueSourceTest() {

    override fun provider(): KeyValueSource = AndroidEncryptedKeyValueSource(RuntimeEnvironment.getApplication())

}