package core.web3.interactor.eth.contract.impl.metadata

import com.google.gson.annotations.SerializedName
import core.essentials.misc.utils.GsonUtils
import core.web3.model.Asset
import core.web3.model.AssetType
import org.tinylog.Logger
import java.math.BigInteger

abstract class MetadataExtractor : IMetadataExtractor {

    override suspend fun extract(
        uri: String,
        contractAddress: String,
        wallet: String,
        token: BigInteger
    ): Asset? {
        Logger.debug("extract :: uri={}", uri)
        val json = extractJson(uri) ?: return null
        val meta = GsonUtils.toObject(json, Metadata::class.java) ?: return null
        Logger.debug("extract :: meta={}", meta)
        return Asset(
            uid = token.toString(),
            name = meta.name,
            icon = meta.image,
            type = AssetType.ERC721,
            address = contractAddress,
            description = meta.description,
            wallet = wallet,
            attrs = meta.attributes.associate { it.traitType to it.value }
        )
    }

    protected abstract suspend fun extractJson(uri: String): String?

    data class Metadata(
        @SerializedName("name")
        val name: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("description")
        val description: String?,
        @SerializedName("attributes")
        val attributes: List<Attribute> = emptyList()
    ) {
        data class Attribute(
            @SerializedName("trait_type")
            val traitType: String,
            @SerializedName("value")
            val value: String
        )
    }
}