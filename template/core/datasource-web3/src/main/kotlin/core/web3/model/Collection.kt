package core.web3.model

import core.essentials.cache.ICacheKey
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Date

data class Collection(
    val slug: String? = null,
    val address: String,
    val platform: Platform,
    val assetType: AssetType,
    val blockchain: Blockchain,
    val name: String? = null,
    val createDate: Date? = null,
    val imageUrl: String? = null,
    val bannerUrl: String? = null,
    val description: String? = null,
    val floorPrice: BigDecimal? = null,
    val externalUrl: String? = null,
    val twitterName: String? = null,
    val instagramName: String? = null,
    val telegramUrl: String? = null,
    val discordUrl: String? = null,
    val wikiUrl: String? = null,
    val collectionFees: Map<String, String>? = null,
    val marketFeeAddress: String? = null,
    val marketFeeAmount: BigInteger? = null,
    val feeEnforced: Boolean
) {
    data class PersistableCacheKey(
        val address: String,
        val platform: Platform,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<Collection>

    data class InMemoryCacheKey(
        val address: String,
        val platform: Platform,
        override val ttl: Long = ICacheKey.TTL_5_MINUTES
    ) : ICacheKey<Collection>
}