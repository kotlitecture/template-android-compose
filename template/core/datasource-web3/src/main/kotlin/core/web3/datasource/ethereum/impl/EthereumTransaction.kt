package core.web3.datasource.ethereum.impl

import core.essentials.exception.DataException
import core.essentials.cache.ICacheKey
import core.essentials.misc.extensions.repeatWhile
import core.essentials.misc.extensions.times
import core.web3.IWeb3Context
import core.web3.datasource.ethereum.IEthereumTransaction
import core.web3.extensions.weiFromEth
import core.web3.extensions.weiToEth
import core.web3.extensions.weiToGwei
import core.web3.model.Blockchain
import core.web3.model.TransactionState
import core.web3.model.Wallet
import core.web3.model.eth.UserTx
import org.tinylog.Logger
import java.math.BigDecimal
import java.math.BigInteger
import java.util.UUID

class EthereumTransaction(
    private val userTx: UserTx,
    private val context: IWeb3Context,
    private var transactionAddress: String? = null,
    private val iconUrl: String? = null,
    private val description: String? = null,
    private val name: String? = null,
) : IEthereumTransaction {

    private val id: String = UUID.randomUUID().toString()

    private var state: TransactionState = TransactionState.NotStarted()

    override suspend fun getState(): TransactionState {
        val lastState = state
        if (lastState.completed) return lastState
        try {
            val api = context.getEtherscanSource()
            val address = getTransactionAddress() ?: return TransactionState.NotStarted()
            val newState = api.getTransactionState(address)
            state = newState
            return newState
        } catch (e: Exception) {
            return TransactionState.Error(e.message ?: e.stackTraceToString())
        }
    }

    override fun getName(): String? {
        return name
    }

    override fun getDescription(): String? {
        return description
    }

    override fun getIcon(): String? {
        return iconUrl
    }

    override fun getTransactionUrl(): String? {
        return getTransactionAddress()?.let { getBlockchain().getTransactionAddressUrl(it) }
    }

    override fun getTransactionAddress(): String? {
        return transactionAddress
    }

    override fun getWallet(): Wallet {
        return userTx.from
    }

    override fun getBlockchain(): Blockchain {
        return Blockchain.ETH
    }

    override suspend fun commit(): String {
        return commit { prepareRaw() }
    }

    override suspend fun awaitState(): TransactionState {
        repeatWhile(block = this::getState) { !it.completed }
        if (state !is TransactionState.Success) {
            throw DataException(msg = "wrong state: $state(${state.message})")
        }
        return state
    }

    override suspend fun commitAndAwait(): TransactionState {
        transactionAddress = commit()
        return awaitState()
    }

    override suspend fun commit(maxFee: BigDecimal): String {
        return commit {
            val tx = prepareRaw()
            val transactionFee = tx.getTransactionFeeInEth()
            if (transactionFee > maxFee) {
                val msg = "transaction fee exceeds the limit : $maxFee -> $transactionFee"
                throw DataException(msg = msg)
            }
            val maxGasPrice = maxFee.weiFromEth().divide(tx.gasLimit)
            tx.copy(
                gasPrice = null,
                maxFeePerGas = maxGasPrice,
                maxPriorityFeePerGas = BigInteger.ONE
            )
        }
    }

    override suspend fun isExecutable(maxFee: BigDecimal): Boolean {
        return try {
            val tx = prepareRaw()
            val transactionFee = tx.getTransactionFeeInEth()
            transactionFee <= maxFee
        } catch (e: Exception) {
            Logger.error(e, "[RS] isExecutable")
            false
        }
    }

    override suspend fun getTransactionFee(): BigDecimal {
        if (transactionAddress != null) return BigDecimal.ZERO
        val tx = prepareRaw()
        return tx.gasLimit.multiply(tx.gasPrice ?: tx.maxFeePerGas).weiToEth()
    }

    override suspend fun getPayableAmount(): BigDecimal {
        return userTx.value.weiToEth()
    }

    override suspend fun getRaw(): UserTx {
        return userTx
    }

    override suspend fun prepareRaw(): UserTx {
        val repeats = 3
        val inc = 0.25f
        for (i in 1..repeats) {
            try {
                val times = inc * i + 1
                val ethereumSource = context.getEthereumSource()
                val block = ethereumSource.getBlock()!!
                val maxFeePerGas = block.baseFeePerGas.times(times)
                val tx = userTx.copy(
                    gasPrice = null,
                    gasLimit = block.gasLimit,
                    maxFeePerGas = maxFeePerGas.max(userTx.maxFeePerGas ?: BigInteger.ZERO)
                        .plus(BigInteger.ONE),
                    maxPriorityFeePerGas = (userTx.maxPriorityFeePerGas
                        ?: (maxFeePerGas - block.baseFeePerGas)).plus(BigInteger.ONE)
                )
                Logger.debug(
                    "getRawWithMarketGas :: times={}, gasPrice={}, gasLimit={}, maxFeePerGas={}, maxPriorityFeePerGas={}",
                    times,
                    tx.gasPrice?.weiToGwei(),
                    tx.gasLimit.weiToGwei(),
                    tx.maxFeePerGas?.weiToGwei(),
                    tx.maxPriorityFeePerGas?.weiToGwei(),
                )
                val gasLimit = ethereumSource.getGasLimit(tx)!!
                return tx.copy(gasLimit = gasLimit.times(1.5f))
            } catch (e: DataException) {
                Logger.error(e)
                if (i == repeats || e.isInsufficientFunds()) throw e
            }
        }
        throw DataException(msg = "Cannot proceed. Try again later.")
    }

    private suspend fun commit(provider: suspend () -> UserTx): String {
        transactionAddress = transactionAddress ?: context.getCacheSource()
            .get(TransactionKey(id)) { context.getEthereumSource().commit(provider()) }
        if (transactionAddress.isNullOrEmpty()) throw DataException(msg = "Cannot proceed. Idk why.")
        return transactionAddress!!
    }

    private data class TransactionKey(
        private val id: String, override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<String> {
        override fun throwErrors(): Boolean = true
    }

}