package core.web3.model.eth

import java.math.BigInteger

data class Mint(
    val maxSupply: BigInteger?,
    val collectionName: String?,
    val collectionIcon: String?,
    val collectionAddress: String,

    val tokens: List<BigInteger>,

    val to: String,
    val from: String,
    val txHash: String,
    val input: String,
    val value: BigInteger,
    val gasUsed: BigInteger,
    val gasLimit: BigInteger,
    val gasPrice: BigInteger,
    val blockNumber: BigInteger,
    val blockTimestamp: BigInteger,
    val maxPriority: BigInteger?,
    val maxFeePerGas: BigInteger,

    val functionId: String?,
    val functionName: String?,
    val functionSignature: String?,
    val functionInputs: List<Pair<String, String>>,
    val functionOutputs: List<Pair<String, String>>,
) {

    fun isMintable(): Boolean {
        return functionId != null
                && functionName != null
                && functionSignature != null
    }

}