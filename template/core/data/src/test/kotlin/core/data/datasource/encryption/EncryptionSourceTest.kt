package core.data.datasource.encryption

import core.data.datasource.storage.keyvalue.BasicEncryptedKeyValueSource
import core.testing.BaseAndroidUnitTest
import org.junit.Assert
import org.robolectric.RuntimeEnvironment
import org.tinylog.kotlin.Logger
import kotlin.test.Test

class EncryptionSourceTest : BaseAndroidUnitTest() {

    private val encryptionSource: EncryptionSource by lazy {
        BasicEncryptionSource(BasicEncryptedKeyValueSource(RuntimeEnvironment.getApplication()))
    }

    @Test
    fun `encrypted string`() = performTest {
        val text = "AAABBBCCC"

        // from decrypted
        val decryptedText = encryptionSource.fromDecrypted(text)
        Assert.assertEquals(text, decryptedText.decrypted())
        Assert.assertNotEquals(text, decryptedText.encrypted())

        // from encrypted
        val encryptedText = encryptionSource.fromEncrypted(decryptedText.encrypted())
        Assert.assertEquals(decryptedText.decrypted(), encryptedText.decrypted())
        Assert.assertEquals(decryptedText.encrypted(), encryptedText.encrypted())
        Assert.assertEquals(text, encryptedText.decrypted())

        Assert.assertSame(decryptedText.password(), encryptedText.password())

        Logger.debug("encrypted string :: decrypted={}, encrypted={}, password={}",
            text,
            encryptedText.encrypted(),
            encryptedText.password()?.decodeToString()
        )
    }

}