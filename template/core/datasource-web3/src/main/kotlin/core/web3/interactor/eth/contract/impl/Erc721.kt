package core.web3.interactor.eth.contract.impl

import core.essentials.cache.ICacheKey
import core.essentials.misc.extensions.withJobAsync
import core.web3.IWeb3Context
import core.web3.datasource.ethereum.IEthereumTransaction
import core.web3.datasource.ethereum.impl.EthereumTransaction
import core.web3.interactor.eth.contract.IErc721
import core.web3.interactor.eth.contract.impl.metadata.Base64Extractor
import core.web3.interactor.eth.contract.impl.metadata.HttpExtractor
import core.web3.interactor.eth.contract.impl.metadata.IpfsExtractor
import core.web3.model.Asset
import core.web3.model.AssetContract
import core.web3.model.AssetType
import core.web3.model.ITransaction
import core.web3.model.Wallet
import core.web3.model.eth.UserTx
import kotlinx.coroutines.awaitAll
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import java.math.BigInteger

internal class Erc721(
    contractAddress: String,
    context: IWeb3Context,
    wallet: Wallet,
    web3: Web3j,
) : BaseContract(
    contractAddress,
    context,
    wallet,
    web3
), IErc721 {

    override suspend fun name(): String? {
        return callStringMethod(
            method = "name",
            key = StringMethodKey("name", contractAddress)
        )
    }

    override suspend fun symbol(): String? {
        return callStringMethod(
            method = "symbol",
            key = StringMethodKey("symbol", contractAddress)
        )
    }

    override suspend fun tokenURI(token: BigInteger): String? {
        return callStringMethod(
            method = "tokenURI",
            inputs = listOf(Uint256(token)),
            key = TokenStringMethodKey("tokenURI", token, contractAddress)
        )
    }

    override suspend fun maxSupply(): BigInteger? {
        return callNumberMethod(
            method = "maxSupply",
            key = BigIntegerMethodKey("maxSupply", contractAddress)
        )
    }

    override suspend fun totalSupply(): BigInteger? {
        return callNumberMethod(
            method = "totalSupply"
        )
    }

    override suspend fun balanceOf(address: String): BigInteger? {
        return callNumberMethod(
            method = "balanceOf",
            inputs = listOf(Address(address))
        )
    }

    override suspend fun isApprovedForAll(operatorAddress: String): Boolean {
        val key = IsApprovedForAllKey(wallet.address, contractAddress, operatorAddress)
        return cacheSource.get(key) {
            callMethod(
                method = "isApprovedForAll",
                inputs = listOf(Address(wallet.address), Address(operatorAddress)),
                output = OUTPUT_BOOLEAN
            ) as? Boolean ?: false
        } ?: false
    }

    override suspend fun setApprovalForAll(
        operatorAddress: String,
        approved: Boolean
    ): ITransaction {
        val key = SetApprovalForAllKey(wallet.address, contractAddress, operatorAddress)
        return cacheSource.get(key) {
            val function = Function(
                "setApprovalForAll",
                listOf(Address(operatorAddress), Bool(true)),
                emptyList()
            )
            val input = FunctionEncoder.encode(function)
            val tx = UserTx(
                to = contractAddress,
                from = wallet,
                input = input,
                value = BigInteger.ZERO,
                gasPrice = BigInteger.ZERO,
                gasLimit = BigInteger.ZERO
            )
            EthereumTransaction(tx, context)
        }!!
    }

    override suspend fun getAssetContract(): AssetContract {
        val attrs = listOf(
            withJobAsync { name() },
            withJobAsync { symbol() },
            withJobAsync { maxSupply() }
        ).awaitAll().map { it.getOrNull() }
        return cacheSource.get(AssetContract.CacheKey(contractAddress)) {
            AssetContract(
                address = contractAddress,
                assetType = AssetType.ERC721,
                name = attrs.getOrNull(0) as? String,
                symbol = attrs.getOrNull(1) as? String,
                maxSupply = attrs.getOrNull(2) as? BigInteger
            )
        }!!
    }

    override suspend fun getAsset(id: BigInteger): Asset? {
        return cacheSource.get(
            Asset.CacheKey(
                id = id.toString(),
                address = contractAddress,
                type = AssetType.ERC721
            )
        ) {
            val url = tokenURI(id) ?: return@get null
            val httpSource = context.getHttpSource()
            val extractors = listOf(
                IpfsExtractor(httpSource),
                HttpExtractor(httpSource),
                Base64Extractor()
            )
            val extractor = extractors.find { it.canExtract(url) }
            extractor?.extract(url, contractAddress, wallet.address, id)
        }
    }

    private data class IsApprovedForAllKey(
        val walletAddress: String,
        val contractAddress: String,
        val operatorAddress: String,
        override val ttl: Long = ICacheKey.TTL_5_SECONDS
    ) : ICacheKey<Boolean>

    private data class SetApprovalForAllKey(
        val walletAddress: String,
        val contractAddress: String,
        val operatorAddress: String,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<IEthereumTransaction>

}