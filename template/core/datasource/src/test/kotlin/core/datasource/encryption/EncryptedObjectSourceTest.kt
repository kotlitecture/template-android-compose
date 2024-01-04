package core.datasource.encryption

import core.testing.MyUnitTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.tinylog.kotlin.Logger
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class EncryptedObjectSourceTest : MyUnitTest() {

    @Inject
    lateinit var encryptedObjectSource: IEncryptedObjectSource

    @Test
    fun `encrypted string`() = performTest {
        val text = "AAABBBCCC"

        // from decrypted
        val decryptedText = encryptedObjectSource.fromDecryptedString(text)
        Assert.assertEquals(text, decryptedText.decryptedValue())
        Assert.assertNotEquals(text, decryptedText.encryptedValue())

        // from encrypted
        val encryptedText = encryptedObjectSource.fromEncryptedString(decryptedText.encryptedValue())
        Assert.assertEquals(decryptedText.decryptedValue(), encryptedText.decryptedValue())
        Assert.assertEquals(decryptedText.encryptedValue(), encryptedText.encryptedValue())
        Assert.assertEquals(text, encryptedText.decryptedValue())

        Assert.assertSame(decryptedText.getPassword(), encryptedText.getPassword())

        Logger.debug("encrypted string :: decrypted={}, encrypted={}, password={}",
            text,
            encryptedText.encryptedValue(),
            encryptedText.getPassword()?.decodeToString()
        )
    }

}