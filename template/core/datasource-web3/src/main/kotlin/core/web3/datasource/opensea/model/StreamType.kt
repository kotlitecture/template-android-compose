package core.web3.datasource.opensea.model

import com.google.gson.annotations.SerializedName

enum class StreamType {
    @SerializedName("item_metadata_updated")
    ItemMetadataUpdated,

    @SerializedName("item_listed")
    ItemListed,

    @SerializedName("item_sold")
    ItemSold,

    @SerializedName("item_transferred")
    ItemTransferred,

    @SerializedName("item_received_offer")
    ItemReceivedOffer,

    @SerializedName("item_received_bid")
    ItemReceivedBid,

    @SerializedName("item_cancelled")
    ItemCancelled,

    @SerializedName("collection_offer")
    CollectionOffer,

    @SerializedName("trait_offer")
    TraitOffer
}