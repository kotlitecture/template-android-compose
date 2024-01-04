package core.web3.model.eth

import java.math.BigInteger

data class Tx(
    val hash: String,
    val to: String,
    val from: String,
    val input: String,
    val value: BigInteger,
    val gasUsed: BigInteger,
    val gasLimit: BigInteger,
    val gasPrice: BigInteger,
    val maxFeePerGas: BigInteger,
    val maxPriority: BigInteger?,
    val blockNumber: BigInteger,
    val status: String?
)