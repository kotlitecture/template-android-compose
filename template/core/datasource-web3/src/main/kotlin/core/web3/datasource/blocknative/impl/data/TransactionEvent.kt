package core.web3.datasource.blocknative.impl.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Date

data class TransactionEvent(
    @SerializedName("timeStamp")
    val timeStamp: Date?,
    @SerializedName("event")
    val event: Event?
) {

    data class Event(
        @SerializedName("categoryCode")
        val categoryCode: String,
        @SerializedName("eventCode")
        val eventCode: String,
        @SerializedName("transaction")
        val transaction: Transaction?
    )

    data class Transaction(
        @SerializedName("status")
        val status: Status?,
        @SerializedName("hash")
        val hash: String?,
        @SerializedName("from")
        val from: String?,
        @SerializedName("to")
        val to: String?,
        @SerializedName("value")
        val value: String?,
        @SerializedName("gas")
        val gas: BigInteger?,
        @SerializedName("nonce")
        val nonce: BigInteger?,
        @SerializedName("blockNumber")
        val blockNumber: BigInteger?,
        @SerializedName("input")
        val input: String?,
        @SerializedName("maxFeePerGas")
        val maxFeePerGas: BigInteger?,
        @SerializedName("maxFeePerGasGwei")
        val maxFeePerGasGwei: BigDecimal?,
        @SerializedName("maxPriorityFeePerGasGwei")
        val maxPriorityFeePerGasGwei: BigDecimal?,
        @SerializedName("maxPriorityFeePerGas")
        val maxPriorityFeePerGas: BigInteger?,
        @SerializedName("baseFeePerGas")
        val baseFeePerGas: BigInteger?,
        @SerializedName("baseFeePerGasGwei")
        val baseFeePerGasGwei: BigDecimal?,
        @SerializedName("gasUsed")
        val gasUsed: BigInteger?,
        @SerializedName("watchedAddress")
        val watchedAddress: String,
        @SerializedName("internalTransactions")
        val internalTransactions: List<InternalTransaction>?,
        @SerializedName("netBalanceChanges")
        val netBalanceChanges: List<NetBalanceChange>?
    )

    data class InternalTransaction(
        @SerializedName("type")
        val type: String,
        @SerializedName("from")
        val from: String,
        @SerializedName("to")
        val to: String,
        @SerializedName("input")
        val input: String,
        @SerializedName("gas")
        val gas: BigInteger,
        @SerializedName("gasUsed")
        val gasUsed: BigInteger,
        @SerializedName("value")
        val value: BigInteger,
        @SerializedName("contractCall")
        val contractCall: ContractCall?
    )

    data class ContractCall(
        @SerializedName("contractType")
        val contractType: String,
        @SerializedName("contractAddress")
        val contractAddress: String,
        @SerializedName("methodName")
        val methodName: String,
        @SerializedName("contractAlias")
        val contractAlias: String?,
        @SerializedName("contractName")
        val contractName: String?,
        @SerializedName("params")
        val params: Map<String, Any>?
    )

    data class NetBalanceChange(
        @SerializedName("address")
        val address: String,
        @SerializedName("balanceChanges")
        val balanceChanges: List<BalanceChange>?
    )

    data class BalanceChange(
        @SerializedName("delta")
        val delta: BigInteger,
        @SerializedName("asset")
        val asset: Asset
    )

    data class Asset(
        @SerializedName("type")
        val type: String,
        @SerializedName("contractAddress")
        val contractAddress: String?,
        @SerializedName("symbol")
        val symbol: String?,
        @SerializedName("id")
        val id: String?
    )

    enum class Status {
        @SerializedName("pending")
        Pending,

        @SerializedName("pending-simulation")
        PendingSimulation,

        @SerializedName("stuck")
        Stuck,

        @SerializedName("speedup")
        Speedup,

        @SerializedName("cancel")
        Cancel,

        @SerializedName("confirmed")
        Confirmed,

        @SerializedName("failed")
        Failed,

        @SerializedName("dropped")
        Dropped,
    }
}