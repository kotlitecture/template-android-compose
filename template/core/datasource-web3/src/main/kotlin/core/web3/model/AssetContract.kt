package core.web3.model

import core.essentials.cache.ICacheKey
import java.math.BigInteger

data class AssetContract(
    val name: String?,
    val symbol: String?,
    val address: String,
    val assetType: AssetType,
    val maxSupply: BigInteger?
) {
    data class CacheKey(
        val address: String,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<AssetContract>
}