package core.web3.utils

import org.web3j.crypto.Hash
import org.web3j.crypto.Sign
import org.web3j.utils.Numeric
import java.math.BigInteger
import java.util.Random

object HashUtils {

    fun generateRandomSalt(domain: String? = null): String {
        return if (domain != null) {
            val keccak256 = Hash.sha3String(domain).substring(0, 10)
            val salt = ByteArray(28)
            System.arraycopy(Numeric.hexStringToByteArray(keccak256), 0, salt, 0, 10)
            System.arraycopy(ByteArray(20), 0, salt, 10, 20)
            Random().nextBytes(ByteArray(8))
            System.arraycopy(ByteArray(8), 0, salt, 30, 8)
            "0x${Numeric.toHexString(salt)}"
        } else {
            val randomBytes = ByteArray(8)
            Random().nextBytes(randomBytes)
            "0x${Numeric.toHexString(randomBytes).padStart(64, '0')}"
        }
    }

    fun generateRandomNumericSalt(domain: String? = null): BigInteger {
        return if (domain != null) {
            val keccak256 = Numeric.hexStringToByteArray(Hash.sha3String(domain).substring(0, 10))
            val salt = ByteArray(28)
            System.arraycopy(keccak256, 0, salt, 0, 10)
            System.arraycopy(ByteArray(20), 0, salt, 10, 20)
            Random().nextBytes(ByteArray(8))
            System.arraycopy(ByteArray(8), 0, salt, 30, 8)
            BigInteger(1, salt)
        } else {
            val randomBytes = ByteArray(8)
            Random().nextBytes(randomBytes)
            BigInteger(1, randomBytes)
        }
    }

    fun signatureDataToString(signatureData: Sign.SignatureData): String {
        val r = Numeric.toHexStringNoPrefix(signatureData.r)
        val s = Numeric.toHexStringNoPrefix(signatureData.s)
        val v = Numeric.toHexStringNoPrefix(signatureData.v)
        return "0x$r$s$v"
    }

}