package core.web3.datasource.opensea.impl.mapper

import core.essentials.misc.utils.GsonUtils
import core.web3.datasource.opensea.impl.data.GetAssets
import core.web3.datasource.opensea.impl.data.GetCollection
import core.web3.model.Asset
import core.web3.model.AssetType
import core.web3.model.Blockchain
import core.web3.model.Collection
import core.web3.model.Platform

object DataMapper {

    fun toCollection(from: GetCollection.Response, address: String): Collection? {
        val collection = from.collection ?: return null
        val contract = collection.contracts?.firstOrNull()
        return Collection(
            blockchain = Blockchain.byChain(from.chain ?: contract?.chain),
            platform = Platform.OpenSea,
            name = collection.name,
            slug = collection.slug,
            imageUrl = collection.imageUrl,
            bannerUrl = collection.bannerImageUrl,
            description = collection.description,
            externalUrl = collection.externalUrl,
            twitterName = collection.twitterName,
            discordUrl = collection.discordUrl,
            telegramUrl = collection.telegramUrl,
            instagramName = collection.instagramName,
            wikiUrl = collection.wikiUrl,
            createDate = collection.createDate,
            address = address,
            assetType = AssetType.by(from.schema ?: contract?.schema),
            collectionFees = collection.fees?.collectionFee?.mapValues { it.value.toString() },
            marketFeeAddress = collection.fees?.getMarketFeeAddress(),
            marketFeeAmount = collection.fees?.getMarketFeeAmount(),
            feeEnforced = collection.feeEnforced
        )
    }

    fun toAsset(from: GetAssets.Asset, wallet: String? = null): Asset {
        return Asset(
            uid = from.tokenId.lowercase(),
            wallet = wallet?.lowercase(),
            name = from.name,
            description = null,
            icon = from.imageUrl,
            address = from.contract.address.lowercase(),
            type = AssetType.by(from.contract.schemaName),
            attrs = mapOf(
                Asset.ATTR_RANK to from.rarity?.rank?.toString(),
                Asset.ATTR_SCORE to from.rarity?.score?.toPlainString(),
                Asset.ATTR_TRAITS to GsonUtils.toString(from.traits),
                Asset.ATTR_COLLECTION_NAME to from.collection.name,
                Asset.ATTR_COLLECTION_SLUG to from.collection.slug
            ).filterValues { it != null }.mapValues { it.value!! })
    }

}