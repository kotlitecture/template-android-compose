package core.web3.datasource.opensea.model

import com.google.gson.annotations.SerializedName
import core.web3.model.Blockchain
import java.math.BigInteger

/**
 * https://docs.opensea.io/reference/retrieve-offers
 */
data class Order(
    @SerializedName("order_hash")
    val orderHash: String,
    @SerializedName("type", alternate = ["order_type"])
    val type: String,
    @SerializedName("price")
    val price: Price?,
    @SerializedName("current_price")
    val currentPrice: BigInteger?,
    @SerializedName("protocol_address")
    val protocolAddress: String,
    @SerializedName("protocol_data")
    val protocolData: ProtocolData,
    @SerializedName("cancelled")
    val cancelled: Boolean = false,
    @SerializedName("finalized")
    val finalized: Boolean = false,
    @SerializedName("marked_invalid")
    val invalid: Boolean = false,
    @SerializedName("maker_asset_bundle")
    val assetBundle: MakerAssetBundle?
) {

    companion object {
        private val TRICKY_PRICE = BigInteger("1000000000000")
    }

    fun isListing(): Boolean {
        val price = getOrderPrice() ?: return false
        return type == "basic"
                && price != TRICKY_PRICE
                && getTokenId() != null
                && !cancelled
                && !finalized
                && !invalid
    }

    fun getMaker(): String {
        return protocolData.parameters.offerer
    }

    fun getOrderPrice(): BigInteger? {
        return currentPrice ?: price?.currentPrice?.value
    }

    fun getStartDate(): Long {
        return protocolData.parameters.startTime * 1000
    }

    fun getEndDate(): Long {
        return protocolData.parameters.endTime * 1000
    }

    fun getTokenId(): String? {
        val assets = assetBundle?.assets
        if (!assets.isNullOrEmpty()) {
            return assets.first().tokenId
        }
        return protocolData.parameters.offer.firstOrNull()?.identifierOrCriteria
    }

    fun getTokenIconUrl(): String? {
        val assets = assetBundle?.assets
        if (!assets.isNullOrEmpty()) {
            return assets.first().imageUrl
        }
        return null
    }

    data class Response(
        @SerializedName("listings", alternate = ["orders"])
        val orders: List<Order>?,
        @SerializedName("next")
        val next: String?
    )

    data class RequestAll(
        val slug: String,
        val limit: Int = 100,
        val next: String? = null,
    )

    data class RequestSpecific(
        val address: String,
        val chain: Blockchain,
        val tokens: List<String>,
        val next: String? = null,
    )

    data class Price(
        @SerializedName("current") val currentPrice: CurrentPrice
    )

    data class CurrentPrice(
        @SerializedName("currency") val currency: String,
        @SerializedName("decimals") val decimals: Int,
        @SerializedName("value") val value: BigInteger
    )

    data class Offer(
        @SerializedName("itemType") val itemType: Int,
        @SerializedName("token") val token: String,
        @SerializedName("identifierOrCriteria") val identifierOrCriteria: String,
        @SerializedName("startAmount") val startAmount: BigInteger,
        @SerializedName("endAmount") val endAmount: BigInteger
    )

    data class Consideration(
        @SerializedName("itemType") val itemType: Int,
        @SerializedName("token") val token: String,
        @SerializedName("identifierOrCriteria") val identifierOrCriteria: String,
        @SerializedName("startAmount") val startAmount: BigInteger,
        @SerializedName("endAmount") val endAmount: BigInteger,
        @SerializedName("recipient") val recipient: String
    )

    data class Parameters(
        @SerializedName("offerer") val offerer: String,
        @SerializedName("offer") val offer: List<Offer>,
        @SerializedName("consideration") val consideration: List<Consideration>,
        @SerializedName("startTime") val startTime: Long,
        @SerializedName("endTime") val endTime: Long,
        @SerializedName("orderType") val orderType: Int,
        @SerializedName("zone") val zone: String,
        @SerializedName("zoneHash") val zoneHash: String,
        @SerializedName("salt") val salt: String,
        @SerializedName("conduitKey") val conduitKey: String,
        @SerializedName("totalOriginalConsiderationItems") val totalOriginalConsiderationItems: Int,
        @SerializedName("counter") val counter: String
    )

    data class ProtocolData(
        @SerializedName("parameters") val parameters: Parameters,
        @SerializedName("signature") val signature: String?
    )

    data class MakerAssetBundle(
        @SerializedName("assets")
        val assets: List<Asset>,
//        @SerializedName("maker")
//        val maker: Any?,
//        @SerializedName("slug")
//        val slug: Any?,
//        @SerializedName("name")
//        val name: Any?,
//        @SerializedName("description")
//        val description: Any?,
//        @SerializedName("external_link")
//        val externalLink: Any?,
//        @SerializedName("asset_contract")
//        val assetContract: Any?,
//        @SerializedName("permalink")
//        val permalink: Any?,
//        @SerializedName("seaport_sell_orders")
//        val seaportSellOrders: Any?
    )

    data class Asset(
        @SerializedName("id")
        val id: Long?,
        @SerializedName("token_id")
        val tokenId: String?,
        @SerializedName("image_url")
        val imageUrl: String?,
    )

    data class AssetContract(
        @SerializedName("address")
        val address: String,
        @SerializedName("asset_contract_type")
        val assetContractType: String,
        @SerializedName("chain_identifier")
        val chainIdentifier: String,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("nft_version")
        val nftVersion: Any?,
        @SerializedName("opensea_version")
        val openseaVersion: Any?,
        @SerializedName("owner")
        val owner: Int,
        @SerializedName("schema_name")
        val schemaName: String,
        @SerializedName("symbol")
        val symbol: String,
        @SerializedName("total_supply")
        val totalSupply: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("external_link")
        val externalLink: String,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("default_to_fiat")
        val defaultToFiat: Boolean,
        @SerializedName("dev_buyer_fee_basis_points")
        val devBuyerFeeBasisPoints: Int,
        @SerializedName("dev_seller_fee_basis_points")
        val devSellerFeeBasisPoints: Int,
        @SerializedName("only_proxied_transfers")
        val onlyProxiedTransfers: Boolean,
        @SerializedName("opensea_buyer_fee_basis_points")
        val openseaBuyerFeeBasisPoints: Int,
        @SerializedName("opensea_seller_fee_basis_points")
        val openseaSellerFeeBasisPoints: Int,
        @SerializedName("buyer_fee_basis_points")
        val buyerFeeBasisPoints: Int,
        @SerializedName("seller_fee_basis_points")
        val sellerFeeBasisPoints: Int,
        @SerializedName("payout_address")
        val payoutAddress: String
    )

    data class Collection(
        @SerializedName("banner_image_url")
        val bannerImageUrl: String,
        @SerializedName("chat_url")
        val chatUrl: Any?,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("default_to_fiat")
        val defaultToFiat: Boolean,
        @SerializedName("description")
        val description: String,
        @SerializedName("dev_buyer_fee_basis_points")
        val devBuyerFeeBasisPoints: String,
        @SerializedName("dev_seller_fee_basis_points")
        val devSellerFeeBasisPoints: String,
        @SerializedName("discord_url")
        val discordUrl: Any?,
        @SerializedName("display_data")
        val displayData: DisplayData,
        @SerializedName("external_url")
        val externalUrl: String,
        @SerializedName("featured")
        val featured: Boolean,
        @SerializedName("featured_image_url")
        val featuredImageUrl: String,
        @SerializedName("hidden")
        val hidden: Boolean,
        @SerializedName("safelist_request_status")
        val safelistRequestStatus: String,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("is_subject_to_whitelist")
        val isSubjectToWhitelist: Boolean,
        @SerializedName("large_image_url")
        val largeImageUrl: String,
        @SerializedName("medium_username")
        val mediumUsername: Any?,
        @SerializedName("name")
        val name: String,
        @SerializedName("only_proxied_transfers")
        val onlyProxiedTransfers: Boolean,
        @SerializedName("opensea_buyer_fee_basis_points")
        val openseaBuyerFeeBasisPoints: String,
        @SerializedName("opensea_seller_fee_basis_points")
        val openseaSellerFeeBasisPoints: Int,
        @SerializedName("payout_address")
        val payoutAddress: String,
        @SerializedName("require_email")
        val requireEmail: Boolean,
        @SerializedName("short_description")
        val shortDescription: Any?,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("telegram_url")
        val telegramUrl: Any?,
        @SerializedName("twitter_username")
        val twitterUsername: Any?,
        @SerializedName("instagram_username")
        val instagramUsername: Any?,
        @SerializedName("wiki_url")
        val wikiUrl: Any?,
        @SerializedName("is_nsfw")
        val isNsfw: Boolean,
        @SerializedName("fees")
        val fees: Fees,
        @SerializedName("is_rarity_enabled")
        val isRarityEnabled: Boolean,
        @SerializedName("is_creator_fees_enforced")
        val isCreatorFeesEnforced: Boolean
    )

    data class DisplayData(
        @SerializedName("card_display_style")
        val cardDisplayStyle: String,
        @SerializedName("images")
        val images: Any?
    )

    data class Fees(
        @SerializedName("seller_fees")
        val sellerFees: Map<String, Int>,
        @SerializedName("opensea_fees")
        val openseaFees: Map<String, Int>
    )
}