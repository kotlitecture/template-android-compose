package core.web3.interactor.eth.contract

import core.web3.AbstractTest
import org.junit.jupiter.api.Assertions
import kotlin.test.Test

class UniswapPairTest : AbstractTest() {

    private val interactor by lazy {
        val wallet = context.getEthWallet()
        context.getEthereumInteractor()
            .contractUniswapPair(wallet, "0xa43fe16908251ee70ef74718545e4fe6c5ccec9f")
    }

    @Test
    fun `token0`() = performTest {
        Assertions.assertEquals(
            "0x6982508145454Ce325dDbE47a25d4ec3d2311933".lowercase(),
            interactor.token0().contractAddress
        )
    }

    @Test
    fun `token1`() = performTest {
        Assertions.assertEquals(
            "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2".lowercase(),
            interactor.token1().contractAddress
        )
    }

}