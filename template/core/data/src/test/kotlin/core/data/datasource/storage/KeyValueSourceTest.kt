package core.data.datasource.storage

import core.data.datasource.storage.keyvalue.AndroidKeyValueSource
import core.data.datasource.storage.keyvalue.KeyValueSource
import org.robolectric.RuntimeEnvironment

class KeyValueSourceTest : BaseKeyValueSourceTest() {

    override fun provider(): KeyValueSource = AndroidKeyValueSource(RuntimeEnvironment.getApplication())

}