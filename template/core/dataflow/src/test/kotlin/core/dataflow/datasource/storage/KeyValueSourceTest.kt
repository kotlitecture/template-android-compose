package core.dataflow.datasource.storage

import org.robolectric.RuntimeEnvironment

class KeyValueSourceTest : BaseKeyValueSourceTest() {

    override fun provider(): KeyValueSource = AndroidKeyValueSource(RuntimeEnvironment.getApplication())

}