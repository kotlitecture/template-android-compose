package core.web3.interactor.eth.contract

import core.web3.AbstractTest
import core.web3.extensions.weiFromEth
import core.web3.extensions.weiToEth
import org.junit.jupiter.api.Assertions
import org.tinylog.kotlin.Logger
import java.math.BigDecimal
import kotlin.test.Test

class UniswapV2RouterTest : AbstractTest() {

    private val interactor by lazy {
        val wallet = context.getEthWallet()
        context.getEthereumInteractor().contractUniswapV2Router(wallet)
    }

    @Test
    fun `weth`() = performTest {
        Assertions.assertEquals("0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2", interactor.weth())
    }

    @Test
    fun `getPair`() = performTest {
        val tokenAddress = "0x6982508145454ce325ddbe47a25d4ec3d2311933"
        val pair = interactor.getPair(tokenAddress)
        Assertions.assertEquals(tokenAddress, pair.token0().contractAddress)
        Assertions.assertEquals(interactor.weth(), pair.token1().contractAddress)
        Logger.debug("pair :: {}", pair.contractAddress)
    }

    @Test
    fun `getTokensForEth + getEthForTokens`() = performTest {
        val ethIn = BigDecimal("1")
        val tokenAddress = "0x6982508145454ce325ddbe47a25d4ec3d2311933"
        val tokensAmount = interactor.getTokensForEth(ethIn.weiFromEth(), tokenAddress)
        val ethOut = interactor.getEthForTokens(tokensAmount, tokenAddress).weiToEth()
        Logger.debug("tokensAmount :: {}", tokensAmount)
        Logger.debug("ethOut :: {}", ethOut)
        Assertions.assertTrue(ethOut < ethIn)
    }

    @Test
    fun `buyTokens`() = performTest {
        val ethIn = BigDecimal("0.01").weiFromEth()
        val tokenAddress = "0x6982508145454ce325ddbe47a25d4ec3d2311933"
        val transaction = interactor.buyTokens(ethIn, tokenAddress)
        Logger.debug("cost :: {}", transaction.getTransactionFee())
    }

}