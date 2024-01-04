package core.web3.datasource.opensea.impl.data

import com.google.gson.annotations.SerializedName
import org.web3j.crypto.StructuredData
import org.web3j.crypto.StructuredData.EIP712Message
import org.web3j.crypto.StructuredData.Entry
import java.math.BigInteger

object CreateOrder {

    data class Listing(
        @SerializedName("parameters")
        val parameters: Order,
        @SerializedName("signature")
        val signature: String,
        @SerializedName("protocol_address")
        val protocolAddress: String
    )

    fun createMessage(order: Order, verifyingContract: String, salt: String): EIP712Message {
        return EIP712Message(
            HashMap(
                mapOf(
                    "EIP712Domain" to eip712Domain.toMutableList(),
                    "OrderComponents" to orderComponents.toMutableList(),
                    "OfferItem" to offerItem.toMutableList(),
                    "ConsiderationItem" to considerationItem.toMutableList()
                )
            ),
            "OrderComponents",
            order.toMap(),
            StructuredData.EIP712Domain(
                "Seaport",
                "1.5",
                "1",
                verifyingContract,
                salt
            )
        )
    }

    data class Order(
        @SerializedName("offerer") val offerer: String,
        @SerializedName("zone") val zone: String,
        @SerializedName("offer") val offer: List<OfferItem>,
        @SerializedName("consideration") val consideration: List<ConsiderationItem>,
        @SerializedName("orderType") val orderType: Int,
        @SerializedName("startTime") val startTime: BigInteger,
        @SerializedName("endTime") val endTime: BigInteger,
        @SerializedName("zoneHash") val zoneHash: String,
        @SerializedName("salt") val salt: BigInteger,
        @SerializedName("conduitKey") val conduitKey: String,
        @SerializedName("counter") val counter: BigInteger,
        @SerializedName("totalOriginalConsiderationItems") val items: Int = consideration.size,
    ) {
        fun toMap(): Map<String, Any> {
            return mapOf(
                "offerer" to offerer,
                "zone" to zone,
                "offer" to offer.map { it.toMap() },
                "consideration" to consideration.map { it.toMap() },
                "orderType" to orderType,
                "startTime" to startTime,
                "endTime" to endTime,
                "zoneHash" to zoneHash,
                "salt" to salt,
                "conduitKey" to conduitKey,
                "counter" to counter
            )
        }
    }

    data class OfferItem(
        @SerializedName("itemType") val itemType: Int,
        @SerializedName("token") val token: String,
        @SerializedName("identifierOrCriteria") val identifierOrCriteria: BigInteger,
        @SerializedName("startAmount") val startAmount: BigInteger,
        @SerializedName("endAmount") val endAmount: BigInteger
    ) {
        fun toMap(): Map<String, Any> {
            return mapOf(
                "itemType" to itemType,
                "token" to token,
                "identifierOrCriteria" to identifierOrCriteria,
                "startAmount" to startAmount,
                "endAmount" to endAmount
            )
        }
    }

    data class ConsiderationItem(
        @SerializedName("itemType") val itemType: Int = 0,
        @SerializedName("token") val token: String = "0x0000000000000000000000000000000000000000",
        @SerializedName("identifierOrCriteria") val identifierOrCriteria: BigInteger = BigInteger.ZERO,
        @SerializedName("startAmount") val startAmount: BigInteger,
        @SerializedName("endAmount") val endAmount: BigInteger,
        @SerializedName("recipient") val recipient: String
    ) {
        fun toMap(): Map<String, Any> {
            return mapOf(
                "itemType" to itemType,
                "token" to token,
                "identifierOrCriteria" to identifierOrCriteria,
                "startAmount" to startAmount,
                "endAmount" to endAmount,
                "recipient" to recipient
            )
        }
    }

    private val orderComponents = listOf(
        Entry("offerer", "address"),
        Entry("zone", "address"),
        Entry("offer", "OfferItem[]"),
        Entry("consideration", "ConsiderationItem[]"),
        Entry("orderType", "uint8"),
        Entry("startTime", "uint256"),
        Entry("endTime", "uint256"),
        Entry("zoneHash", "bytes32"),
        Entry("salt", "uint256"),
        Entry("conduitKey", "bytes32"),
        Entry("counter", "uint256")
    )

    private val offerItem = listOf(
        Entry("itemType", "uint8"),
        Entry("token", "address"),
        Entry("identifierOrCriteria", "uint256"),
        Entry("startAmount", "uint256"),
        Entry("endAmount", "uint256")
    )

    private val considerationItem = listOf(
        Entry("itemType", "uint8"),
        Entry("token", "address"),
        Entry("identifierOrCriteria", "uint256"),
        Entry("startAmount", "uint256"),
        Entry("endAmount", "uint256"),
        Entry("recipient", "address")
    )

    private val eip712Domain = listOf(
        Entry("name", "string"),
        Entry("version", "string"),
        Entry("chainId", "uint256"),
        Entry("verifyingContract", "address")
    )

}