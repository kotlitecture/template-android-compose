package core.web3.datasource.opensea.impl.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

object GetAssets {

    data class Response(
        @SerializedName("next")
        val next: String?,
        @SerializedName("assets")
        val assets: List<Asset>
    )

    data class Asset(
        @SerializedName("token_id")
        val tokenId: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("image_url", alternate = ["image_preview_url", "image_thumbnail_url"])
        val imageUrl: String,
        @SerializedName("permalink")
        val permalink: String,
        @SerializedName("description")
        val description: String?,
        @SerializedName("rarity_data")
        val rarity: Rarity?,
        @SerializedName("traits")
        val traits: List<Trait> = emptyList(),
        @SerializedName("asset_contract")
        val contract: AssetContract,
        @SerializedName("collection")
        val collection: AssetCollection
    )

    data class Rarity(
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("score")
        val score: BigDecimal?
    )

    data class AssetContract(
        @SerializedName("schema_name")
        val schemaName: String,
        @SerializedName("address")
        val address: String
    )

    data class AssetCollection(
        @SerializedName("slug")
        val slug: String,
        @SerializedName("name")
        val name: String
    )

    data class Trait(
        @SerializedName("trait_type")
        val type: String,
        @SerializedName("trait_count")
        val count: Int,
        @SerializedName("value")
        val value: String
    )

}