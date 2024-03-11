package core.dataflow.misc.utils

import core.testing.MyUnitTest
import org.junit.Assert
import kotlin.test.Test

class EncryptUtilsTest : MyUnitTest() {

    @Test
    fun `aes encryption`() {
        val text = "AAABBBCCC"
        val password = EncryptUtils.generatePassword()
        val encoded = EncryptUtils.encryptAes(password, text)
        val decoded = EncryptUtils.decryptAes(password, encoded)
        Assert.assertNotEquals(text, encoded)
        Assert.assertEquals(text, decoded)
    }

}