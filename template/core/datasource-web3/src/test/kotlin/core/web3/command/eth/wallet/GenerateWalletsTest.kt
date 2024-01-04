package core.web3.command.eth.wallet

import core.web3.AbstractTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class GenerateWalletsTest : AbstractTest() {

    @Test
    fun `generate wallets`() = performTest {
        val number = 3
        val api = context.getEthereumSource()
        val wallets = GenerateWallets(number).execute(context)
        Assertions.assertEquals(number, wallets.size)
        wallets.forEach { wallet ->
            val balance = api.getBalance(wallet.address)
            Assertions.assertEquals(BigInteger.ZERO, balance)
        }
    }

}