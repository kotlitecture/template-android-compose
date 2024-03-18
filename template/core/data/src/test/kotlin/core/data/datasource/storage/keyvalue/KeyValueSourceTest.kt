package core.data.datasource.storage.keyvalue

import org.robolectric.RuntimeEnvironment

class KeyValueSourceTest : BaseKeyValueSourceTest() {

    override fun provider(): KeyValueSource = BasicKeyValueSource(RuntimeEnvironment.getApplication())

}