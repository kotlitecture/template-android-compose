package core.essentials.misc.utils

import core.essentials.AbstractTest
import org.junit.jupiter.api.Assertions
import java.util.UUID
import kotlin.test.Test

class EncryptUtilsTest : AbstractTest() {

    @Test
    fun `aes encryption`() {
        val text = "AAABBBCCC"
        val password = EncryptUtils.generatePassword()
        val encoded = EncryptUtils.encryptAes(password, text)
        val decoded = EncryptUtils.decryptAes(password, encoded)
        Assertions.assertNotEquals(text, encoded)
        Assertions.assertEquals(text, decoded)
    }

}