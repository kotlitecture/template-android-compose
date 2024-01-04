package core.web3.model

import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.utils.Numeric
import java.security.Security

enum class Blockchain(
    val code: String,
    val chain: String
) {

    ETH("ETH", "ethereum") {
        override fun generateWallet(): Wallet {
            init()
            val key = Keys.createEcKeyPair()
            val credentials = Credentials.create(key)
            val address = credentials.address
            val privateKey = Numeric.toHexStringNoPrefix(key.privateKey)
            return Wallet(
                address = address,
                privateKey = privateKey,
                blockchain = ETH
            )
        }

        override fun createWallet(privateKey: String): Wallet {
            init()
            val credentials = Credentials.create(privateKey)
            return Wallet(
                address = credentials.address,
                privateKey = privateKey,
                blockchain = ETH
            )
        }

        override fun getAddressUrl(address: String): String {
            return "https://etherscan.io/address/$address"
        }

        override fun getTransactionAddressUrl(address: String): String {
            return "https://etherscan.io/tx/$address"
        }
    },

    BTC("BTC", "bnb"),
    Undefined("undefined", "undefined")

    ;

    open fun generateWallet(): Wallet {
        return Wallet(
            address = "",
            privateKey = "",
            blockchain = this
        )
    }

    open fun createWallet(privateKey: String): Wallet? {
        return null
    }

    open fun getAddressUrl(address: String): String {
        return ""
    }

    open fun getTransactionAddressUrl(address: String): String {
        return ""
    }

    companion object {
        private val init = lazy {
            Security.removeProvider("BC")
            Security.addProvider(BouncyCastleProvider())
        }

        private val byCode = Blockchain.entries.associateBy { it.code }
        private val byChain = Blockchain.entries.associateBy { it.chain }

        fun init() = init.value

        fun byCode(code: String?) = byCode[code] ?: Undefined

        fun byChain(chain: String?) = byChain[chain] ?: Undefined

        fun isAddress(text: String): Boolean = text.startsWith("0x", ignoreCase = true)
    }
}