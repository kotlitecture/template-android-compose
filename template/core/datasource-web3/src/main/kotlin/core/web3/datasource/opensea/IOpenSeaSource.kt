package core.web3.datasource.opensea

import core.web3.datasource.opensea.model.Listing
import core.web3.datasource.opensea.model.Order
import core.web3.datasource.opensea.model.StreamEvent
import core.web3.datasource.opensea.model.StreamFilter
import core.web3.model.Asset
import core.web3.model.Collection
import core.web3.model.CollectionStat
import core.web3.model.Wallet
import core.web3.model.eth.UserTx
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface IOpenSeaSource {

    // https://support.opensea.io/hc/en-us/articles/4449355421075
    fun getZone(): String = "0x004C00500000aD104D7DBd00e3ae0A5C00560C00"
    fun getTokenApprovals(): String = "0x1E0049783F008A0085193E00003D00cd54003c71"
    fun getProtocolAddress(): String = "0x00000000000000ADc04C56Bf30aC9d3c0aAF14dC"
    fun getZoneHash(): String = "0x0000000000000000000000000000000000000000000000000000000000000000"
    fun getConduitKey(): String =
        "0x0000007b02230091a7ed01230072f7006a004d60a8d4e71d599b8104250f0000"

    suspend fun getBySlug(slug: String): Collection?

    suspend fun getStat(slug: String): CollectionStat

    suspend fun getByAddress(contractAddress: String): Collection?

    suspend fun fetchByAddress(contractAddress: String): Collection?

    suspend fun getEvents(filter: StreamFilter): Flow<StreamEvent<*>>

    suspend fun getListings(filter: Order.RequestAll): Order.Response

    suspend fun getListings(filter: Order.RequestSpecific): List<Order>

    suspend fun getAssets(contractAddress: String, walletAddress: String): List<Asset>

    suspend fun getAssets(keys: List<Asset.AssetKey>): List<Asset>

    suspend fun createListing(wallet: Wallet, asset: Asset, price: BigDecimal, duration: Long)

    suspend fun fulfillListing(listing: Listing, wallet: Wallet): UserTx

}