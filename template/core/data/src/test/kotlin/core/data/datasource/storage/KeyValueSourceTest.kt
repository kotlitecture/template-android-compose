package core.data.datasource.storage

import core.data.datasource.storage.keyvalue.BasicKeyValueSource
import core.data.datasource.storage.keyvalue.KeyValueSource
import org.robolectric.RuntimeEnvironment

class KeyValueSourceTest : BaseKeyValueSourceTest() {

    override fun provider(): KeyValueSource = BasicKeyValueSource(RuntimeEnvironment.getApplication())

}