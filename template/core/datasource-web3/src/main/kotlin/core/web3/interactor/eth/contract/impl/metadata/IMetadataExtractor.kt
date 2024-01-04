package core.web3.interactor.eth.contract.impl.metadata

import core.web3.model.Asset
import java.math.BigInteger

interface IMetadataExtractor {

    fun canExtract(uri: String): Boolean

    suspend fun extract(
        uri: String,
        contractAddress: String,
        wallet: String,
        token: BigInteger
    ): Asset?

}