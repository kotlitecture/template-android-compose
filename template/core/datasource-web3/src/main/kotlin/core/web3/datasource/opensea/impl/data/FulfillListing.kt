package core.web3.datasource.opensea.impl.data

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

object FulfillListing {

    data class Request(
        @SerializedName("listing")
        val listing: Listing,
        @SerializedName("fulfiller")
        val fulfiller: Fulfiller
    )

    data class Response(
        @SerializedName("fulfillment_data")
        val data: Data
    )

    data class Listing(
        @SerializedName("hash")
        val hash: String,
        @SerializedName("chain")
        val chain: String,
        @SerializedName("protocol_address")
        val protocolAddress: String,
    )

    data class Fulfiller(
        @SerializedName("address")
        val address: String
    )

    data class Data(
        @SerializedName("transaction")
        val transaction: Transaction
    )

    data class Transaction(
        @SerializedName("function")
        val function: String,
        @SerializedName("to")
        val to: String,
        @SerializedName("value")
        val value: BigInteger,
        @SerializedName("input_data")
        val inputData: InputData
    )

    data class InputData(
        @SerializedName("parameters")
        val parameters: Map<String, Any>
    )

}