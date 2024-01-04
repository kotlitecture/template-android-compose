package core.web3.datasource.ethereum.impl

import core.essentials.exception.DataException
import core.essentials.cache.ICacheKey
import core.essentials.cache.ICacheSource
import core.essentials.http.HttpSource
import core.essentials.misc.extensions.infiniteFlow
import core.essentials.misc.extensions.isCancellationException
import core.essentials.misc.extensions.repeatWhile
import core.essentials.misc.helpers.RequestThrottler
import core.web3.datasource.ethereum.IEthereumSource
import core.web3.datasource.ethereum.impl.mapper.WalletMapper
import core.web3.datasource.ethereum.impl.mapper.ModelMapper
import core.web3.model.eth.Block
import core.web3.model.eth.GasPrice
import core.web3.model.eth.Transfer
import core.web3.model.eth.Tx
import core.web3.model.eth.UserTx
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import org.tinylog.Logger
import org.web3j.abi.EventEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.Response
import org.web3j.protocol.core.RpcErrors
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.EthGasPrice
import org.web3j.protocol.core.methods.response.EthGetBalance
import org.web3j.protocol.core.methods.response.EthLog
import org.web3j.protocol.core.methods.response.Transaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ChainIdLong
import org.web3j.utils.Numeric
import java.math.BigInteger
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.jvm.optionals.getOrNull

class EthereumSource(
    private val cacheSource: ICacheSource,
    private val httpSource: HttpSource,
    private val nodeUrl: String,
    private val pollInterval: Long,
) : IEthereumSource {

    private val minimumPollInterval = TimeUnit.SECONDS.toMillis(1)

    private val transactionThrottling = RequestThrottler()

    private val web3 by lazy { Web3j.build(HttpService(nodeUrl, httpSource.okhttp)) }

    override suspend fun getBlock(): Block? =
        cacheSource.get(LatestBlockKey(minimumPollInterval)) {
            Logger.info("quick_task :: get block", "fsf")
            web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                .send()
                .block
                ?.let(ModelMapper::toBlock)
                ?.also { block -> cacheSource.put(BlockKey(block.number), block) }
        }

    override suspend fun getBlock(number: BigInteger): Block? =
        cacheSource.get(BlockKey(number)) {
            web3.ethGetBlockByNumber(DefaultBlockParameter.valueOf(number), false)
                .send()
                .block
                ?.let(ModelMapper::toBlock)
        }

    override suspend fun getBlockLive(): Flow<Block> {
        var prevBlock: Block? = null
        var blockResolveTime: Long = pollInterval
        return infiniteFlow("getBlockLive") {
            var delay = blockResolveTime
            val block = getBlock()
            if (block != null) {
                blockResolveTime = prevBlock
                    ?.takeIf { it.number == block.number.minus(BigInteger.ONE) }
                    ?.let { block.getDate().time - it.getDate().time }
                    ?: blockResolveTime
                val remainingDelay =
                    block.getDate().time + blockResolveTime - System.currentTimeMillis()
                if (remainingDelay >= 0) {
                    delay = remainingDelay
                } else {
                    delay = minimumPollInterval
                }
                prevBlock = block
                emit(block)
            }
            delay
        }
    }

    override suspend fun getGasPrice(): GasPrice? {
        val ethGasPrice = ethGasPrice() ?: return null
        return ModelMapper.toGasPrice(ethGasPrice)
    }

    override suspend fun getGasPriceLive(): Flow<GasPrice> {
        return infiniteFlow("getGasPriceLive") {
            getGasPrice()?.let { emit(it) }
            pollInterval
        }
    }

    override suspend fun getBalance(address: String): BigInteger {
        return web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
    }

    override suspend fun isWallet(address: String): Boolean {
        val transactions = ethGetTransactionCount(address)
        if (transactions <= BigInteger.ZERO) return false
        val code = cacheSource.get(CodeKey(address)) {
            web3.ethGetCode(address, DefaultBlockParameterName.LATEST).send().code
        }
        return code.isNullOrEmpty() || code == "0x"
    }

    override suspend fun getBalance(addressList: List<String>): List<BigInteger> {
        if (addressList.isEmpty()) return emptyList()
        val request = web3.newBatch()
        val block = DefaultBlockParameterName.LATEST
        addressList.forEach { address ->
            request.add(web3.ethGetBalance(address, block))
        }
        val response = request.send()
        return response.responses
            .map { it as EthGetBalance }
            .map { it.balance }
    }

    override suspend fun getTx(hash: String): Tx? {
        val transaction = ethGetTransactionByHash(hash) ?: return null
        val receipt = ethGetTransactionReceipt(hash) ?: return null
        return ModelMapper.toTx(transaction, receipt)
    }

    override suspend fun getTokenTransfer(hash: String): Transfer? {
        return ethGetTransactionReceipt(hash)?.logs
            ?.let { ModelMapper.toTokenTransfer(hash, it, true) }
    }

    override suspend fun getTokenTransfers(addresses: List<String>): Flow<List<Transfer>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTokenTransfers(): Flow<List<Transfer>> {
        return getMintLogs()
            .map { ethLog ->
                ethLog.logs
                    .asSequence()
                    .mapNotNull { it.get() as? EthLog.LogResult<*> }
                    .mapNotNull { it as? EthLog.LogObject }
                    .map { it.get() }
                    .groupBy { it.transactionHash }
                    .mapNotNull { ModelMapper.toTokenTransfer(it.key, it.value) }
            }
            .filter { it.isNotEmpty() }
    }

    override suspend fun getGasLimit(tx: UserTx): BigInteger? {
        val to = tx.to
        val input = tx.input
        val value = tx.value
        val gasLimit = tx.gasLimit
        val from = tx.from.address
        val gasPrice = tx.gasPrice
        val nonce = getNonce(from)
        return web3
            .ethEstimateGas(
                org.web3j.protocol.core.methods.request.Transaction(
                    from,
                    nonce,
                    gasPrice,
                    gasLimit,
                    to,
                    value,
                    input,
                    null,
                    tx.maxPriorityFeePerGas,
                    tx.maxFeePerGas
                )
            )
            .send()
            .let(this::validate)
            .amountUsed
    }

    override suspend fun getNonce(address: String): BigInteger {
        return cacheSource.get(NonceKey(address)) {
            ethGetTransactionCount(address)
        }!!
    }

    override suspend fun commit(tx: UserTx): String {
        return transactionThrottling.throttle(tx.from.address) {
            Logger.debug("commit :: {}", tx)
            val credentials = WalletMapper.toCredentials(tx.from)
            val address = credentials.address
            val nonce = getNonce(address)
            cacheSource.remove(NonceKey(address))
            val rawTransaction =
                when {
                    tx.gasPrice != null -> {
                        RawTransaction.createTransaction(
                            nonce,
                            tx.gasPrice,
                            tx.gasLimit,
                            tx.to,
                            tx.value,
                            tx.input.orEmpty()
                        )
                    }

                    else -> {
                        RawTransaction.createTransaction(
                            ChainIdLong.MAINNET,
                            nonce,
                            tx.gasLimit,
                            tx.to,
                            tx.value,
                            tx.input.orEmpty(),
                            tx.maxPriorityFeePerGas,
                            tx.maxFeePerGas
                        )
                    }
                }
            val message = TransactionEncoder.signMessage(rawTransaction, credentials)
            val hexValue = Numeric.toHexString(message)
            val response = web3.ethSendRawTransaction(hexValue).send()
            val hash = response.let(this::validate).transactionHash
            repeatWhile(
                block = { ethGetTransactionCount(address) },
                repeatIf = { it == nonce }
            )
            Logger.debug("tx hash :: {} -> {}", hash, tx)
            hash
        }
    }

    private suspend fun getMintLogs(): Flow<EthLog> {
        val filter = createErc721LogFilter()
        return infiniteFlow("getMintLogs") {
            val filterId = web3.ethNewFilter(filter).send().filterId
            val filterNotFoundPattern = Pattern.compile(FILTER_NOT_FOUND_PATTERN)
            Logger.debug("getMintLogs :: filter={}, started={}", filterId, pollInterval)
            ethLog@ while (currentCoroutineContext().isActive) {
                var delay = pollInterval
                try {
                    val ethLog = web3.ethGetFilterChanges(filterId).send()
                    if (ethLog.hasError()) {
                        val error = ethLog.error
                        val message = error.message
                        Logger.debug("getMintLogs :: hasError={}", message)
                        when {
                            error.code == RpcErrors.FILTER_NOT_FOUND -> break@ethLog
                            filterNotFoundPattern.matcher(message).find() -> break@ethLog
                            else -> throw DataException(msg = message)
                        }
                    }
                    if (!ethLog.logs.isNullOrEmpty()) {
                        Logger.debug("getMintLogs :: got something :: filter={}", filterId)
                        if (!currentCoroutineContext().isActive) break@ethLog
                        emit(ethLog)
                    } else {
                        delay = minimumPollInterval
                    }
                } catch (e: Throwable) {
                    if (!e.isCancellationException()) {
                        Logger.error(e, "getMintLogs error")
                    }
                    throw e
                }
                delay(delay)
            }
            pollInterval
        }
    }

    private fun createErc721LogFilter(
        fromBlock: DefaultBlockParameterName = DefaultBlockParameterName.LATEST,
        toBlock: DefaultBlockParameterName = DefaultBlockParameterName.LATEST
    ): EthFilter {
        val transferEvent = Event(
            "Transfer", listOf<TypeReference<*>>(
                TypeReference.create(Address::class.java),
                TypeReference.create(Address::class.java),
                TypeReference.create(Uint256::class.java)
            )
        )
        val transferEventSignature = EventEncoder.encode(transferEvent)
        val filter = EthFilter(
            fromBlock, toBlock, emptyList()
        )
        filter.addSingleTopic(transferEventSignature)
        return filter
    }

    private suspend fun ethGetTransactionByHash(hash: String) =
        cacheSource.get(TransactionKey(hash)) {
            web3.ethGetTransactionByHash(hash).send().transaction.getOrNull()
        }

    private suspend fun ethGetTransactionReceipt(hash: String) =
        cacheSource.get(TransactionReceiptKey(hash)) {
            web3.ethGetTransactionReceipt(hash).send().transactionReceipt.getOrNull()
        }

    private fun ethGetTransactionCount(address: String): BigInteger {
        return web3
            .ethGetTransactionCount(address, DefaultBlockParameterName.LATEST)
            .send()
            .transactionCount
    }

    private suspend fun ethGasPrice() =
        cacheSource.get(EthGasPriceKey(pollInterval)) {
            web3.ethGasPrice().send()
        }

    private fun <R : Response<*>> validate(response: R): R {
        if (response.hasError()) {
            val error = response.error
            throw DataException(error.code, error.message)
        }
        return response
    }

    data class TransactionKey(
        val hash: String,
        override val ttl: Long = ICacheKey.TTL_1_SECOND
    ) : ICacheKey<Transaction>

    data class TransactionReceiptKey(
        val hash: String,
        override val ttl: Long = ICacheKey.TTL_1_SECOND
    ) : ICacheKey<TransactionReceipt>

    data class EthGasPriceKey(
        override val ttl: Long
    ) : ICacheKey<EthGasPrice>

    data class LatestBlockKey(
        override val ttl: Long
    ) : ICacheKey<Block>

    data class BlockKey(
        val number: BigInteger,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<Block>

    data class CodeKey(
        val address: String,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<String>

    data class NonceKey(
        val address: String,
        override val ttl: Long = ICacheKey.TTL_60_SECONDS
    ) : ICacheKey<BigInteger>

    companion object {
        private const val FILTER_NOT_FOUND_PATTERN = "(?i)\\bfilter\\s+not\\s+found\\b"
    }

}