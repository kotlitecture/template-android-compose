package core.web3.datasource.etherscan

import core.web3.AbstractTest
import core.web3.model.TransactionState
import org.junit.jupiter.api.Assertions
import java.math.BigDecimal
import kotlin.test.Test

class EtherscanSourceTest : AbstractTest() {

    private val api: IEtherscanSource = context.getEtherscanSource()

    @Test
    fun `get abi`() = performTest {
        val address = "0xd410356d34d2b583ef057ce2de6d2bcd35fd7329"
        val abi = api.getContractAbi(address)
        Assertions.assertNotNull(abi)
    }

    @Test
    fun `get contract`() = performTest {
        val address = "0xd410356d34d2b583ef057ce2de6d2bcd35fd7329"
        val contract = api.getContractSource(address)!!
        val abi = contract.abi!!
        Assertions.assertTrue(abi.functions.isNotEmpty())
    }

    @Test
    fun `get transaction state`() = performTest {
        Assertions.assertTrue(api.getTransactionState("0x72d09468b0ece0cd8758f6d2d58b526f65eecaadabd3ddc49f0b80ac3d15f69a") is TransactionState.Success)
        Assertions.assertTrue(api.getTransactionState("0x5d77292151bee70858baae718f0e18815fdc22fde6f7c6866bc18bb7cf9fc59b") is TransactionState.Error)
        Assertions.assertTrue(api.getTransactionState("0xqweqe") is TransactionState.Pending)
        Assertions.assertTrue(api.getTransactionState("0x6d9bd9c5e38798f594d96de0ae9127634efc97261000404bf8e4e020e23fd03c") is TransactionState.Pending)
    }

    @Test
    fun `get last price`() = performTest {
        val price = api.getEthPrice()
        Assertions.assertNotEquals(BigDecimal.ZERO, price.usdRate)
        Assertions.assertNotEquals(BigDecimal.ZERO, price.btcRate)
    }

}