package core.web3.interactor.eth.contract

import core.web3.model.Asset
import core.web3.model.AssetContract
import core.web3.model.ITransaction
import java.math.BigInteger

interface IErc721 : IContract {

    suspend fun name(): String?

    suspend fun symbol(): String?

    suspend fun maxSupply(): BigInteger?

    suspend fun totalSupply(): BigInteger?

    suspend fun tokenURI(token: BigInteger): String?

    suspend fun balanceOf(address: String): BigInteger?

    suspend fun isApprovedForAll(operatorAddress: String): Boolean

    suspend fun setApprovalForAll(operatorAddress: String, approved: Boolean): ITransaction

    suspend fun getAssetContract(): AssetContract

    suspend fun getAsset(id: BigInteger): Asset?

}