package core.web3.datasource.alchemy

import core.web3.model.Asset
import core.web3.model.AssetValue

interface IAlchemySource {

    suspend fun getBalances(walletAddress: String): List<AssetValue>

    suspend fun getAsset(contractAddress: String, walletAddress: String): Asset?

}