package core.web3.datasource.opensea.impl.data

import com.google.gson.annotations.SerializedName
import java.math.BigInteger
import java.util.Date

object GetCollection {

    data class Response(
        @SerializedName("collection")
        val collection: Collection?,
        @SerializedName("chain_identifier")
        val chain: String?,
        @SerializedName("schema_name")
        val schema: String?
    )

    data class Collection(
        @SerializedName("name")
        val name: String?,
        @SerializedName("slug")
        val slug: String?,
        @SerializedName("wiki_url")
        val wikiUrl: String?,
        @SerializedName("telegram_url")
        val telegramUrl: String?,
        @SerializedName("twitter_username")
        val twitterName: String?,
        @SerializedName("instagram_username")
        val instagramName: String?,
        @SerializedName("image_url")
        val imageUrl: String?,
        @SerializedName("created_date")
        val createDate: Date?,
        @SerializedName("banner_image_url")
        val bannerImageUrl: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("discord_url")
        val discordUrl: String?,
        @SerializedName("external_url")
        val externalUrl: String?,
        @SerializedName("primary_asset_contracts")
        val contracts: List<Contract>?,
        @SerializedName("fees")
        val fees: Fees?,
        @SerializedName("is_creator_fees_enforced")
        val feeEnforced: Boolean
    )

    data class Contract(
        @SerializedName("address")
        val address: String?,
        @SerializedName("chain_identifier")
        val chain: String?,
        @SerializedName("schema_name")
        val schema: String
    )

    data class Fees(
        @SerializedName("seller_fees")
        val collectionFee: Map<String, BigInteger>?,
        @SerializedName("opensea_fees")
        val marketFee: Map<String, BigInteger>?,
    ) {
        fun getMarketFeeAmount(): BigInteger? {
            return marketFee?.values?.firstOrNull()
        }

        fun getMarketFeeAddress(): String? {
            return marketFee?.keys?.firstOrNull()
        }
    }

}