package core.web3.model.eth

import core.web3.extensions.weiToEth
import core.web3.model.Wallet
import java.math.BigDecimal
import java.math.BigInteger

data class UserTx(
    val to: String,
    val from: Wallet,
    val input: String?,
    val value: BigInteger,
    val gasLimit: BigInteger,
    val gasPrice: BigInteger? = null,
    val maxFeePerGas: BigInteger? = null,
    val maxPriorityFeePerGas: BigInteger? = null
) {
    fun getTransactionFeeInEth(): BigDecimal {
        return gasLimit.multiply(gasPrice ?: maxFeePerGas).weiToEth()
    }
}