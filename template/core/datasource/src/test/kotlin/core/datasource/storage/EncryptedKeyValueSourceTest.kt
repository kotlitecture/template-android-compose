package core.datasource.storage

import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject

@HiltAndroidTest
class EncryptedKeyValueSourceTest : AbstractKeyValueSourceTest() {

    @Inject
    internal lateinit var provider: IEncryptedKeyValueSource

    override fun provider(): IKeyValueSource = provider

}