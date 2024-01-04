package core.web3.model

import java.math.BigDecimal

interface ITransaction {

    /**
     * Returns potential transaction icon.
     */
    fun getIcon(): String?

    /**
     * Returns potential transaction name.
     */
    fun getName(): String?

    /**
     * Returns potential transaction description.
     */
    fun getDescription(): String?

    /**
     * Returns wallet to execute transaction on.
     */
    fun getWallet(): Wallet

    /**
     * Returns transaction address if such is available.
     */
    fun getTransactionAddress(): String?

    /**
     * Returns transaction public url
     */
    fun getTransactionUrl(): String?

    /**
     * Transaction blockchain
     */
    fun getBlockchain(): Blockchain

    /**
     * Returns transaction status.
     */
    suspend fun getState(): TransactionState

    /**
     * Returns transaction status.
     */
    suspend fun awaitState(): TransactionState

    /**
     * Commits transaction and returns its id.
     */
    suspend fun commit(): String

    /**
     * Commits transaction and returns its id.
     */
    suspend fun commitAndAwait(): TransactionState

    /**
     * Commits transaction only if its fee does not exceed the provided one.
     * @return transaction address.
     */
    suspend fun commit(maxFee: BigDecimal): String

    /**
     * Returns true if this transaction can be executed with fee not exceeding the given limit in wallet currency.
     */
    suspend fun isExecutable(maxFee: BigDecimal): Boolean

    /**
     * Returns cost of the transaction in wallet currency.
     */
    suspend fun getTransactionFee(): BigDecimal

    /**
     * Returns transaction payable amount in wallet currency.
     */
    suspend fun getPayableAmount(): BigDecimal

}