package core.web3.datasource.alchemy.impl.data

import core.web3.model.Asset
import core.web3.model.AssetType
import core.web3.model.AssetValue
import java.math.BigDecimal
import java.math.BigInteger

object DataMapper {

    private val assetTypes = mapOf(
        "ERC721" to AssetType.ERC721,
        "ERC1155" to AssetType.ERC1155,
        "NO_SUPPORTED_NFT_STANDARD" to AssetType.NotSupported,
        "NOT_A_CONTRACT" to AssetType.NotContract
    )

    fun toBalances(from: GetTokenBalances.Response): List<AssetValue> {
        return from.result.tokenBalances.map { balance ->
            val amount = runCatching {
                BigInteger(balance.tokenBalance.substring(2), 16)
            }.getOrNull()
            AssetValue(
                address = balance.tokenAddress,
                value = amount?.toBigDecimal() ?: BigDecimal.ZERO
            )
        }
    }

    fun toAsset(from: GetAsset.Response, address: String): Asset {
        return Asset(
            uid = from.result.symbol ?: address,
            wallet = null,
            address = address,
            icon = from.result.logo,
            name = from.result.name,
            type = AssetType.ERC20,
            description = null,
            attrs = mapOf(
                Asset.ATTR_DECIMALS to from.result.decimals.toString()
            )
        )
    }

    private fun toType(from: String?): AssetType {
        return assetTypes[from] ?: AssetType.Unknown
    }

}