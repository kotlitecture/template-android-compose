package core.web3.model.eth

import core.essentials.cache.ICacheKey

data class ContractSource(
    val name: String?,
    val address: String,
    val abi: Abi?,
    val arguments: String?,
    val sourceCode: String?
) {
    data class CacheKey(
        val address: String,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<ContractSource>
}