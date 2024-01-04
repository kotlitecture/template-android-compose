package core.datasource.storage

import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject

@HiltAndroidTest
class KeyValueSourceTest : AbstractKeyValueSourceTest() {

    @Inject
    internal lateinit var provider: IKeyValueSource

    override fun provider(): IKeyValueSource = provider

}