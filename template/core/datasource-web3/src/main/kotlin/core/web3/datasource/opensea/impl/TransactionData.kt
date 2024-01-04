package core.web3.datasource.opensea.impl

import core.web3.model.Wallet
import java.math.BigInteger

data class TransactionData(
    val to: String,
    val from: Wallet,
    val value: BigInteger,
    val methodSignature: String,
    val params: Map<String, Any>
)