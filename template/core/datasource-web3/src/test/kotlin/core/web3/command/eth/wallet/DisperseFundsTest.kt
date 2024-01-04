package core.web3.command.eth.wallet

import core.web3.AbstractTest
import core.web3.datasource.ethereum.IEthereumTransaction
import core.web3.extensions.weiFromEth
import core.web3.extensions.weiToGwei
import core.web3.model.AssetValue
import core.web3.model.Blockchain
import core.web3.model.TransactionState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.tinylog.kotlin.Logger
import java.math.BigDecimal

class DisperseFundsTest : AbstractTest() {

    private val from = Blockchain.ETH
        .createWallet("684759e4cae4f5a9439ba6e2a7e380dde8c9d6bfbbcb8681906c60a056f96ccc")!!

    @Test
    fun `when can disperse to multiple addresses`() = performTest {
        val number = 2
        val wallets = GenerateWallets(number).execute(context)
        val transfers = wallets.map {
            AssetValue(
                address = it.address,
                value = BigDecimal("0.001")
            )
        }
        val transaction = DisperseFunds(from, transfers).execute(context)
        Assertions.assertEquals(transfers.sumOf { it.value }, transaction.getPayableAmount())
        Assertions.assertFalse(transaction.isExecutable(BigDecimal("0.001")))
        Assertions.assertTrue(transaction.isExecutable(BigDecimal("0.015")))
        Assertions.assertTrue(transaction.getState() is TransactionState.NotStarted)

        val tx = (transaction as IEthereumTransaction).prepareRaw()
        val maxGasPrice = BigDecimal("0.015").weiFromEth().divide(tx.gasLimit)
        Logger.debug(
            "gasPrice :: feeInEth={}, gasPrice={}, maxGasPrice={}",
            tx.getTransactionFeeInEth(),
            tx.gasPrice?.weiToGwei(),
            maxGasPrice.weiToGwei()
        )
    }

    @Test
    fun `when not enough funds to disperse`() = performTest {
        val number = 1
        val wallets = GenerateWallets(number).execute(context)
        val transfers = wallets.map {
            AssetValue(
                address = it.address,
                value = BigDecimal("1")
            )
        }
        val transaction = DisperseFunds(from, transfers).execute(context)
        Assertions.assertEquals(BigDecimal("1"), transaction.getPayableAmount())
        Assertions.assertFalse(transaction.isExecutable(BigDecimal("0.02")))
        Assertions.assertTrue(transaction.getState() is TransactionState.NotStarted)
    }

}