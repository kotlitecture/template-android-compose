package core.web3.model

import java.math.BigDecimal

data class CompositeTransaction constructor(private val txList: List<ITransaction>) : ITransaction {
    override fun getIcon(): String? = txList.last().getIcon()
    override fun getName(): String? = txList.last().getName()
    override fun getDescription(): String? = txList.last().getDescription()
    override fun getWallet(): Wallet = txList.last().getWallet()
    override fun getTransactionAddress(): String? = txList.last().getTransactionAddress()
    override fun getTransactionUrl(): String? = txList.last().getTransactionUrl()
    override fun getBlockchain(): Blockchain = txList.last().getBlockchain()
    override suspend fun getState(): TransactionState = txList.last().getState()
    override suspend fun awaitState(): TransactionState = txList.map { it.awaitState() }.last()
    override suspend fun commit(): String {
        txList.take(txList.size - 1).forEach { it.commitAndAwait() }
        return txList.last().commit()
    }

    override suspend fun commit(maxFee: BigDecimal): String {
        txList.take(txList.size - 1).forEach { it.commitAndAwait() }
        return txList.last().commit(maxFee)
    }

    override suspend fun commitAndAwait(): TransactionState =
        txList.map { it.commitAndAwait() }.last()

    override suspend fun isExecutable(maxFee: BigDecimal): Boolean =
        txList.first().isExecutable(maxFee)

    override suspend fun getTransactionFee(): BigDecimal {
        for (tx in txList) {
            val fee = tx.getTransactionFee()
            if (fee.compareTo(BigDecimal.ZERO) != 0) {
                return fee
            }
        }
        return BigDecimal.ZERO
    }


    override suspend fun getPayableAmount(): BigDecimal =
        txList.map { it.getPayableAmount() }.sumOf { it }
}