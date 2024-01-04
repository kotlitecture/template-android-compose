package core.web3.model

import core.essentials.cache.ICacheKey

data class Asset(
    val uid: String,
    val name: String?,
    val icon: String?,
    val wallet: String?,
    val address: String,
    val type: AssetType,
    val description: String?,
    val attrs: Map<String, String>,
) {

    fun getDecimals(): Int {
        return attrs[ATTR_DECIMALS]?.toIntOrNull() ?: 0
    }

    data class AssetKey(
        val id: String,
        val address: String,
    )

    data class CacheKey(
        val id: String?,
        val address: String,
        val type: AssetType,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<Asset>

    companion object {
        const val ATTR_DECIMALS = "decimals"
        const val ATTR_RANK = "rank"
        const val ATTR_SCORE = "score"
        const val ATTR_TRAITS = "traits"
        const val ATTR_COLLECTION_NAME = "collection_name"
        const val ATTR_COLLECTION_SLUG = "collection_slug"
    }
}