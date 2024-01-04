package core.web3.interactor.eth.contract

import core.web3.AbstractTest
import core.web3.model.Wallet
import core.web3.model.eth.TransferEvent
import org.junit.jupiter.api.Assertions
import org.tinylog.Logger
import java.math.BigInteger
import kotlin.test.Test

class Erc20Test : AbstractTest() {

    @Test
    fun `renounced`() = performTest {
        val address = "0x7a705d8f823775af6c8c16ab894909d613443ea1"
        val pair =
            context.getEthereumInteractor().contractUniswapPair(context.getEthWallet(), address)
        Assertions.assertEquals(Wallet.NULL_ADDRESS, pair.token0().owner())
    }

}