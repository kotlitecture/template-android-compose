package core.web3.datasource.ethereum.impl.mapper

import core.web3.model.Wallet
import core.web3.model.eth.Block
import core.web3.model.eth.GasPrice
import core.web3.model.eth.Transfer
import core.web3.model.eth.TransferEvent
import core.web3.model.eth.Tx
import org.web3j.abi.TypeDecoder
import org.web3j.protocol.core.methods.response.EthBlock
import org.web3j.protocol.core.methods.response.EthGasPrice
import org.web3j.protocol.core.methods.response.Log
import org.web3j.protocol.core.methods.response.Transaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.utils.Numeric
import java.math.BigInteger

object ModelMapper {

    fun toBlock(from: EthBlock.Block): Block {
        return Block(
            hash = from.hash,
            size = from.size,
            number = from.number,
            timestamp = from.timestamp,
            gasLimit = from.gasLimit,
            gasUsed = from.gasUsed,
            baseFeePerGas = from.baseFeePerGas
        )
    }

    fun toGasPrice(from: EthGasPrice): GasPrice? {
        return if (from.gasPrice > BigInteger.ZERO) {
            GasPrice(from.gasPrice)
        } else {
            null
        }
    }

    fun toTx(transaction: Transaction, receipt: TransactionReceipt): Tx {
        return Tx(
            hash = transaction.hash,
            to = transaction.to,
            from = transaction.from,
            input = transaction.input,
            value = transaction.value,
            blockNumber = transaction.blockNumber,
            gasPrice = transaction.gasPrice,
            maxPriority = runCatching { transaction.maxPriorityFeePerGas }.getOrNull(),
            maxFeePerGas = transaction.maxFeePerGas ?: transaction.gasPrice,
            gasLimit = transaction.gas,
            gasUsed = receipt.gasUsed,
            status = receipt.status
        )
    }

    fun toTokenTransfer(
        transactionHash: String,
        logs: List<Log>,
        softCheck: Boolean = false
    ): Transfer? {
        val tokens = logs
            .mapNotNull { toToken(it) }
            .takeIf { softCheck || it.size == logs.size }
            ?: return null
        return Transfer(
            contractAddress = tokens.first().address,
            transactionHash = transactionHash,
            events = tokens
        )
    }

    private fun toToken(log: Log): TransferEvent? {
        val topics = log.topics
        val args = topics.takeIf { it.size == 4 } ?: return null
        val from = TypeDecoder.decodeAddress(args[1]).value
            .takeIf { it == Wallet.NULL_ADDRESS }
            ?: return null
        val to = TypeDecoder.decodeAddress(args[2]).value
        val id = Numeric.decodeQuantity(args[3])
        return TransferEvent(
            id = id,
            to = to,
            from = from,
            address = log.address,
            blockNumber = log.blockNumber,
            transactionHash = log.transactionHash
        )
    }

}