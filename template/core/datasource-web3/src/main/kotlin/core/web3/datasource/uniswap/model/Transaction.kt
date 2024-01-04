package core.web3.datasource.uniswap.model

import java.math.BigInteger

data class Transaction(
    val id: String,
    val gasUsed: BigInteger?,
    val gasPrice: BigInteger?,
    val blockNumber: BigInteger,
    val timestamp: BigInteger
)