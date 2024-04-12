package core.data.datasource.keyvalue

import org.junit.Ignore
import org.robolectric.RuntimeEnvironment

@Ignore
class EncryptedSharedPreferencesSourceTest : BaseKeyValueSourceTest() {

    override fun create(): KeyValueSource = EncryptedSharedPreferencesSource(RuntimeEnvironment.getApplication())

}