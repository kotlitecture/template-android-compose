package core.web3.command.eth.contract

import core.web3.AbstractTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GetAbiByTxTest : AbstractTest() {

    @Test
    fun `get implementation abi`() = performTest {
        val txHash = "0xfe8da67572c4419eb8c694d953a409b04ba2f488597bae4d5e51427348027a8d"
        val tx = context.getEthereumSource().getTx(txHash)!!
        val abi = GetContractAbiByTx(tx).execute(context)!!
        val func = abi.getFunctionByInput(tx.input)
        Assertions.assertNotEquals(tx.to, abi.address)
        Assertions.assertNotNull(func)
    }

}