package core.web3.datasource.opensea.impl.data

import com.google.gson.annotations.SerializedName
import core.essentials.misc.utils.GsonUtils
import core.web3.datasource.opensea.model.CollectionOfferEvent
import core.web3.datasource.opensea.model.ItemCancelledEvent
import core.web3.datasource.opensea.model.StreamEvent
import core.web3.datasource.opensea.model.ItemListedEvent
import core.web3.datasource.opensea.model.ItemSoldEvent
import core.web3.datasource.opensea.model.ItemTransferredEvent
import core.web3.datasource.opensea.model.StreamType

object GetEvents {

    val PING = Request(topic = "phoenix", event = "heartbeat")

    data class Request(
        @SerializedName("topic")
        val topic: String,
        @SerializedName("event")
        val event: String,
        @SerializedName("payload")
        val payload: Any = Any(),
        @SerializedName("ref")
        val ref: Int = 0
    )

    data class Event(
        @SerializedName("event")
        val type: StreamType?
    ) {
        fun toItemEvent(json: String): StreamEvent<*>? {
            val itemType = when (type) {
                StreamType.ItemTransferred -> ItemTransferredEvent::class.java
                StreamType.CollectionOffer -> CollectionOfferEvent::class.java
                StreamType.ItemCancelled -> ItemCancelledEvent::class.java
                StreamType.ItemListed -> ItemListedEvent::class.java
                StreamType.ItemSold -> ItemSoldEvent::class.java
                else -> null
            } ?: return null
            return GsonUtils.toObject(json, itemType)
        }
    }

}