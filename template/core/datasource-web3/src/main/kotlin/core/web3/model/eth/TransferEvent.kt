package core.web3.model.eth

import java.math.BigInteger

data class TransferEvent(
    val id: BigInteger,
    val to: String,
    val from: String,
    val address: String,
    val blockNumber: BigInteger,
    val transactionHash: String
)