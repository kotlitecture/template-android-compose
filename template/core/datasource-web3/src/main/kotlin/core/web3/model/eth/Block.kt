package core.web3.model.eth

import java.math.BigInteger
import java.util.Date

data class Block(
    val hash: String,
    val size: BigInteger,
    val number: BigInteger,
    val timestamp: BigInteger,
    val gasLimit: BigInteger,
    val gasUsed: BigInteger,
    val baseFeePerGas: BigInteger
) {
    private val dateLazy by lazy { Date(timestamp.toLong() * 1000) }

    fun getDate(): Date = dateLazy
}