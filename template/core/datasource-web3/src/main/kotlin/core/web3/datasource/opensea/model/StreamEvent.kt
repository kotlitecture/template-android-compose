package core.web3.datasource.opensea.model

import com.google.gson.annotations.SerializedName
import core.web3.extensions.weiToEth
import core.web3.model.Blockchain
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Date

abstract class StreamEvent<P> {

    @SerializedName("event")
    val type: StreamType? = null

    @SerializedName("payload")
    val payload: P? = null

    open fun getStartDate(): Date? = null
    open fun getEndDate(): Date? = null
    open fun getSlug(): String? = null
    open fun getIconUrl(): String? = null
    open fun getTokenId(): String? = null
    open fun getPrice(): BigDecimal? = null
    open fun getOrderHash(): String? = null
    open fun getProtocolAddress(): String? = null
    open fun getPriceRaw(): BigInteger? = null
    open fun getMakerAddress(): String? = null
    open fun getSymbol(): String? = getBlockchain().code
    abstract fun getBlockchain(): Blockchain

    fun isListed(): Boolean = type == StreamType.ItemListed

    data class PayloadWrapper<P>(
        @SerializedName("payload") val payload: P?
    )

    data class Collection(
        @SerializedName("slug") val slug: String
    )

    data class Chain(
        @SerializedName("name") val name: String
    )

    data class Account(
        @SerializedName("address") val address: String
    )

    data class Transaction(
        @SerializedName("hash") val hash: String, @SerializedName("timestamp") val timestamp: Date?
    )

    data class Metadata(
        @SerializedName("animation_url") val animationUrl: String?,
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("metadata_url") val metadataUrl: String?,
        @SerializedName("name") val name: String?,
    )

    data class PaymentToken(
        @SerializedName("address") val address: String,
        @SerializedName("decimals") val decimals: Int?,
        @SerializedName("eth_price") val ethPrice: BigDecimal?,
        @SerializedName("symbol") val symbol: String?,
        @SerializedName("name") val name: String?
    )

    data class Item(
        @SerializedName("chain") val chain: Chain,
        @SerializedName("metadata") val metadata: Metadata,
        @SerializedName("nft_id") val id: String,
        @SerializedName("permalink") val permalink: String
    ) {
        fun getBlockchain(): Blockchain {
            return Blockchain.byChain(chain.name)
        }

        fun getTokenId(): String {
            return id.substringAfterLast("/")
        }
    }

}

class ItemSoldEvent : StreamEvent<StreamEvent.PayloadWrapper<ItemSoldEvent.Payload>>() {
    data class Payload(
        @SerializedName("item") val item: Item,
        @SerializedName("quantity") val quantity: BigInteger,
        @SerializedName("event_timestamp") val eventTimestamp: Date?,
        @SerializedName("collection") val collection: Collection,
        @SerializedName("transaction") val transaction: Transaction?,

        @SerializedName("payment_token") val token: PaymentToken,
        @SerializedName("sale_price") val salePrice: BigInteger,
        @SerializedName("maker") val maker: Account,
        @SerializedName("taker") val taker: Account,
        @SerializedName("order_hash") val orderHash: String,
    )

    override fun getOrderHash(): String? {
        return payload?.payload?.orderHash
    }

    override fun getStartDate(): Date? {
        return Date()
    }

    override fun getIconUrl(): String? {
        return payload?.payload?.item?.metadata?.imageUrl
    }

    override fun getBlockchain(): Blockchain {
        return payload?.payload?.item?.getBlockchain() ?: Blockchain.Undefined
    }

    override fun getPrice(): BigDecimal? {
        return getPriceRaw()?.weiToEth()
    }

    override fun getPriceRaw(): BigInteger? {
        return payload?.payload?.salePrice
    }

    override fun getSlug(): String? {
        return payload?.payload?.collection?.slug
    }

    override fun getTokenId(): String? {
        return payload?.payload?.item?.getTokenId()
    }

    override fun getSymbol(): String? {
        return payload?.payload?.token?.symbol
    }

    override fun getMakerAddress(): String? {
        return payload?.payload?.maker?.address
    }
}

class ItemTransferredEvent :
    StreamEvent<StreamEvent.PayloadWrapper<ItemTransferredEvent.Payload>>() {
    data class Payload(
        @SerializedName("item") val item: Item,
        @SerializedName("quantity") val quantity: BigInteger,
        @SerializedName("event_timestamp") val eventTimestamp: Date?,
        @SerializedName("collection") val collection: Collection,
        @SerializedName("transaction") val transaction: Transaction?,

        @SerializedName("from_account") val from: Account,
        @SerializedName("to_account") val to: Account
    )

    override fun getStartDate(): Date? {
        return payload?.payload?.eventTimestamp
    }

    override fun getIconUrl(): String? {
        return payload?.payload?.item?.metadata?.imageUrl
    }

    override fun getBlockchain(): Blockchain {
        return payload?.payload?.item?.getBlockchain() ?: Blockchain.Undefined
    }

    override fun getSlug(): String? {
        return payload?.payload?.collection?.slug
    }

    override fun getTokenId(): String? {
        return payload?.payload?.item?.getTokenId()
    }
}

class ItemListedEvent : StreamEvent<StreamEvent.PayloadWrapper<ItemListedEvent.Payload>>() {
    data class Payload(
        @SerializedName("item") val item: Item,
        @SerializedName("quantity") val quantity: BigInteger,
        @SerializedName("event_timestamp") val eventTimestamp: Date?,
        @SerializedName("collection") val collection: Collection,

        @SerializedName("listing_date") val listingDate: Date?,
        @SerializedName("base_price") val basePrice: BigInteger,
        @SerializedName("maker") val maker: Account,
        @SerializedName("order_hash") val orderHash: String,
        @SerializedName("protocol_address") val protocolAddress: String?
    )

    override fun getProtocolAddress(): String? {
        return payload?.payload?.protocolAddress
    }

    override fun getOrderHash(): String? {
        return payload?.payload?.orderHash
    }

    override fun getStartDate(): Date? {
        return payload?.payload?.listingDate
    }

    override fun getIconUrl(): String? {
        return payload?.payload?.item?.metadata?.imageUrl
    }

    override fun getBlockchain(): Blockchain {
        return payload?.payload?.item?.getBlockchain() ?: Blockchain.Undefined
    }

    override fun getSlug(): String? {
        return payload?.payload?.collection?.slug
    }

    override fun getTokenId(): String? {
        return payload?.payload?.item?.getTokenId()
    }

    override fun getPrice(): BigDecimal? {
        return getPriceRaw()?.weiToEth()
    }

    override fun getPriceRaw(): BigInteger? {
        return payload?.payload?.basePrice
    }

    override fun getMakerAddress(): String? {
        return payload?.payload?.maker?.address
    }
}

class CollectionOfferEvent : StreamEvent<CollectionOfferEvent.Payload>() {
    data class Payload(
        @SerializedName("quantity") val quantity: BigInteger,
        @SerializedName("event_timestamp") val eventTimestamp: Date?,
        @SerializedName("collection") val collection: Collection,

        @SerializedName("base_price") val basePrice: BigInteger,
        @SerializedName("payment_token") val token: PaymentToken,
        @SerializedName("asset_contract_criteria") val contract: Account,
        @SerializedName("created_date") val createDate: Date?,
        @SerializedName("expiration_date") val expireDate: Date?,
        @SerializedName("maker") val maker: Account,
        @SerializedName("order_hash") val orderHash: String,
    )

    override fun getOrderHash(): String? {
        return payload?.orderHash
    }

    override fun getStartDate(): Date? {
        return payload?.eventTimestamp
    }

    override fun getEndDate(): Date? {
        return payload?.expireDate
    }

    override fun getBlockchain(): Blockchain {
        return Blockchain.Undefined
    }
}

class ItemCancelledEvent : StreamEvent<ItemCancelledEvent.Payload>() {
    data class Payload(
        @SerializedName("quantity") val quantity: BigInteger,
        @SerializedName("event_timestamp") val eventTimestamp: Date?,
        @SerializedName("collection") val collection: Collection,

        @SerializedName("base_price") val basePrice: BigInteger,
        @SerializedName("payment_token") val token: PaymentToken,
        @SerializedName("maker") val maker: Account,
        @SerializedName("order_hash") val orderHash: String,
    )

    override fun getOrderHash(): String? {
        return payload?.orderHash
    }

    override fun getStartDate(): Date? {
        return payload?.eventTimestamp
    }

    override fun getBlockchain(): Blockchain {
        return Blockchain.Undefined
    }
}