package core.web3.model.eth

import com.google.gson.annotations.SerializedName
import core.essentials.cache.ICacheKey

/**
 * https://docs.soliditylang.org/en/v0.8.18/abi-spec.html
 */
data class Abi(
    @SerializedName("address")
    val address: String,
    @SerializedName("constructor")
    val constructor: Constructor?,
    @SerializedName("functions")
    val functions: List<Function>,
    @SerializedName("events")
    val events: List<Event>,
    @SerializedName("errors")
    val errors: List<Error>
) {

    fun getFunctionByInput(input: String): Function? {
        val id = getFunctionId(input)
        return functions.find { it.id == id }
    }

    data class Constructor(
        @SerializedName("inputs")
        val inputs: List<Parameter>
    )

    data class Function(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("payable")
        val payable: Boolean,
        @SerializedName("signature")
        val signature: String,
        @SerializedName("inputs")
        val inputs: List<Parameter>,
        @SerializedName("outputs")
        val outputs: List<Parameter>
    )

    data class Event(
        @SerializedName("name")
        val name: String,
        @SerializedName("inputs")
        val inputs: List<Parameter>
    )

    data class Error(
        @SerializedName("name")
        val name: String,
        @SerializedName("inputs")
        val inputs: List<Parameter>
    )

    data class Parameter(
        @SerializedName("name")
        val name: String?,
        @SerializedName("type")
        val type: String?
    ) {
        fun isAddress(): Boolean = type == "address"
        fun isArray(): Boolean = type != null && type.endsWith("[]")
        fun isBytes(): Boolean = type != null && type.startsWith("bytes")
    }

    data class CacheKey(
        val address: String,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<Abi>

    companion object {
        fun getFunctionId(input: String) = input.take(10)
    }

}