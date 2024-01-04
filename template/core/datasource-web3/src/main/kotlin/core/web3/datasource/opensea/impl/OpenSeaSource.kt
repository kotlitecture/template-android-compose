package core.web3.datasource.opensea.impl

import core.essentials.cache.ICacheKey
import core.essentials.cache.ICacheSource
import core.essentials.http.HttpSource
import core.essentials.http.assertError
import core.essentials.http.assertRateLimited
import core.essentials.misc.helpers.KeyRotator
import core.essentials.misc.helpers.RateLimiter
import core.essentials.misc.utils.GsonUtils
import core.web3.datasource.ethereum.impl.mapper.WalletMapper
import core.web3.datasource.opensea.IOpenSeaSource
import core.web3.datasource.opensea.impl.data.CreateOrder
import core.web3.datasource.opensea.impl.data.FulfillListing
import core.web3.datasource.opensea.impl.data.GetAssets
import core.web3.datasource.opensea.impl.data.GetCollection
import core.web3.datasource.opensea.impl.data.GetCollectionStat
import core.web3.datasource.opensea.impl.data.GetEvents
import core.web3.datasource.opensea.impl.mapper.DataMapper
import core.web3.datasource.opensea.model.Listing
import core.web3.datasource.opensea.model.Order
import core.web3.datasource.opensea.model.StreamEvent
import core.web3.datasource.opensea.model.StreamFilter
import core.web3.extensions.weiFromEth
import core.web3.interactor.eth.IEthereumInteractor
import core.web3.model.Asset
import core.web3.model.Collection
import core.web3.model.CollectionStat
import core.web3.model.Platform
import core.web3.model.Wallet
import core.web3.model.eth.UserTx
import core.web3.utils.HashUtils
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.wss
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.appendPathSegments
import io.ktor.websocket.readBytes
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withTimeout
import org.tinylog.Logger
import org.web3j.crypto.Sign
import org.web3j.crypto.StructuredDataEncoder
import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration.Companion.seconds

class OpenSeaSource(
    private val cacheSource: ICacheSource,
    private val httpSource: HttpSource,
    private val contractSource: IEthereumInteractor,
    httpAccessKeys: List<String> = emptyList(),
    wsAccessKeys: List<String> = emptyList()
) : IOpenSeaSource {

    private val ktor by lazy { httpSource.ktor }
    private val api2Url = "https://api.opensea.io/v2"
    private val api1Url = "https://api.opensea.io/api/v1"
    private val wsUrl = "wss://stream.openseabeta.com/socket/websocket"

    private val httpKeysRotator = KeyRotator(httpAccessKeys)
    private val wsKeysRotator = KeyRotator(wsAccessKeys)
    private val getLimiter = RateLimiter(2)
    private val postLimiter = RateLimiter(2)

    override suspend fun getListings(filter: Order.RequestAll): Order.Response {
        return httpKeysRotator.rotate { key ->
            getLimiter.limit(key) {
                ktor
                    .get {
                        header("X-API-KEY", key)
                        url {
                            url("${api2Url}/listings/collection/${filter.slug}/all")
                            parameter("limit", filter.limit)
                            parameter("next", filter.next)
                        }
                    }
                    .assertRateLimited()
                    .body()
            }
        }
    }

    override suspend fun getListings(filter: Order.RequestSpecific): List<Order> {
        val listings = mutableListOf<Order>()
        val pageSize = 15
        filter.tokens.distinct().chunked(pageSize).forEach { batch ->
            var next = filter.next
            while (currentCoroutineContext().isActive) {
                val page = httpKeysRotator.rotate { key ->
                    getLimiter.limit(key) {
                        ktor
                            .get {
                                header("X-API-KEY", key)
                                url {
                                    url("${api2Url}/orders/${filter.chain.chain}/seaport/listings")
                                    parameter("asset_contract_address", filter.address)
                                    next?.let { parameter("cursor", next) }
                                    batch.forEach { token -> parameter("token_ids", token) }
                                }
                            }
                            .assertRateLimited()
                            .body<Order.Response>()
                    }
                }
                listings.addAll(page.orders ?: emptyList())
                next = page.next
                if (next == null) {
                    break
                }
            }
        }
        return listings.distinctBy { it.getTokenId() }
    }

    override suspend fun getByAddress(contractAddress: String): Collection? {
        val key = Collection.PersistableCacheKey(contractAddress, Platform.OpenSea)
        return cacheSource.get(key) { fetchByAddress(contractAddress) }
    }

    override suspend fun fetchByAddress(contractAddress: String): Collection? {
        val key = Collection.InMemoryCacheKey(contractAddress, Platform.OpenSea)
        return cacheSource.get(key) {
            httpKeysRotator.rotate { key ->
                getLimiter.limit(key) {
                    val response = ktor
                        .get {
                            header("X-API-KEY", key)
                            url {
                                url(api1Url)
                                appendPathSegments("asset_contract", contractAddress)
                                parameter("format", "json")
                            }
                        }
                        .assertRateLimited()
                        .body<GetCollection.Response>()
                    DataMapper.toCollection(response, contractAddress)
                }
            }
        }?.also { collection ->
            val localKey = Collection.PersistableCacheKey(contractAddress, collection.platform)
            cacheSource.put(localKey, collection)
        }
    }

    override suspend fun getBySlug(slug: String): Collection? =
        cacheSource.get(Collection.PersistableCacheKey(slug, Platform.OpenSea)) {
            httpKeysRotator.rotate { key ->
                getLimiter.limit(key) {
                    val response = ktor
                        .get {
                            header("X-API-KEY", key)
                            url {
                                url(api1Url)
                                appendPathSegments("collection", slug)
                                parameter("format", "json")
                            }
                        }
                        .assertRateLimited()
                        .body<GetCollection.Response>()
                    val address = response.collection
                        ?.contracts
                        ?.firstOrNull()
                        ?.address
                        ?: return@limit null
                    DataMapper.toCollection(response, address)
                }
            }
        }

    override suspend fun getEvents(filter: StreamFilter): Flow<StreamEvent<*>> {
        return flow {
            while (currentCoroutineContext().isActive) {
                try {
                    withTimeout(TimeUnit.MINUTES.toMillis(30)) {
                        wsKeysRotator.rotate { key ->
                            ktor.wss("${wsUrl}?token=${key}") {
                                sendSerialized(GetEvents.PING)
                                sendSerialized(
                                    GetEvents.Request(
                                        "collection:${filter.slug}",
                                        "phx_join"
                                    )
                                )
                                val chains = filter.chains
                                val events = filter.events
                                incoming.consumeAsFlow()
                                    .map { it.readBytes().decodeToString() }
                                    .mapNotNull { json ->
                                        val event =
                                            GsonUtils.toObject(json, GetEvents.Event::class.java)
                                        if (event == null) {
                                            Logger.error(
                                                "json parse issue :: event is null {}",
                                                json
                                            )
                                            return@mapNotNull null
                                        }
                                        if (events.isNotEmpty() && !events.contains(event.type)) {
                                            return@mapNotNull null
                                        }
                                        val itemEvent = event.toItemEvent(json)
                                        if (itemEvent == null) {
                                            Logger.error(
                                                "json parse issue :: itemEvent is null :: {}",
                                                json
                                            )
                                        }
                                        itemEvent?.takeIf { chains.isEmpty() || chains.contains(it.getBlockchain()) }
                                    }
                                    .collect { event -> emit(event) }
                            }
                        }
                    }
                } catch (e: TimeoutCancellationException) {
                    Logger.error(e, "reconnect")
                }
            }
        }.retry { true.also { delay(1.seconds) } }
    }

    override suspend fun getStat(slug: String): CollectionStat {
        val key = CollectionStatKey(slug)
        return cacheSource.get(key) {
            httpKeysRotator.rotate { key ->
                getLimiter.limit(key) {
                    ktor
                        .get {
                            header("X-API-KEY", key)
                            url {
                                url("${api1Url}/collection/${slug}/stats")
                            }
                        }
                        .assertRateLimited()
                        .body<GetCollectionStat.Response>()
                        .stats
                }
            }
        }!!
    }

    override suspend fun getAssets(contractAddress: String, walletAddress: String): List<Asset> {
        val assets = mutableSetOf<Asset>()
        var cursor: String? = null
        while (coroutineContext.isActive) {
            val response = getAssets(contractAddress, walletAddress, cursor)
            val responseAssets = response.assets
                .map { DataMapper.toAsset(it, walletAddress) }
            assets.addAll(responseAssets)
            cursor = response.next ?: break
        }
        return assets.toList()
    }

    override suspend fun getAssets(keys: List<Asset.AssetKey>): List<Asset> {
        if (keys.isEmpty()) return emptyList()
        val assets = mutableListOf<Asset>()
        keys.chunked(30).forEach { chunk ->
            val list = httpKeysRotator.rotate { key ->
                getLimiter.limit(key) {
                    val tokens = chunk.map { it.id }
                    val contracts = chunk.map { it.address }
                    val response: GetAssets.Response = ktor
                        .get {
                            header("X-API-KEY", key)
                            url {
                                url("${api1Url}/assets")
                                parameter("limit", 200)
                                tokens.forEach { parameter("token_ids", it) }
                                if (contracts.distinct().size > 1) {
                                    contracts.forEach { parameter("asset_contract_addresses", it) }
                                } else {
                                    parameter("asset_contract_address", contracts.first())
                                }
                            }
                        }
                        .assertRateLimited()
                        .body()
                    response.assets
                        .map { DataMapper.toAsset(it) }
                }
            }
            assets.addAll(list)
        }
        return assets
    }

    override suspend fun createListing(
        wallet: Wallet,
        asset: Asset,
        price: BigDecimal,
        duration: Long
    ) {
        val collectionAddress = asset.address
        val protocolAddress = getProtocolAddress()
        val collection = fetchByAddress(collectionAddress)!!
        val contract = contractSource.contractOpenSeaProtocol(wallet, protocolAddress)

        val startDate = Instant.now()
        val endDate = startDate.plusMillis(duration)
        val counter = contract.getCounter()
        val chain = collection.blockchain

        // create order
        val considerations = mutableListOf<CreateOrder.ConsiderationItem>()
        val totalAmount = price.weiFromEth()
        val totalAmountValue = (totalAmount / 10000.toBigInteger())
        val marketFee = collection.marketFeeAmount ?: BigInteger.ZERO
        val collectionFees = collection.collectionFees
            ?.mapValues { it.value.toBigInteger() }
            ?: emptyMap()
        val collectionFee = collectionFees
            .takeIf { collection.feeEnforced }
            ?.map { it.value }
            ?.sumOf { it }
            ?: BigInteger.ZERO
        val marketAmount = totalAmountValue * marketFee
        val collectionAmount = totalAmountValue * collectionFee
        val offererAmount = totalAmount - marketAmount - collectionAmount
        considerations.add(
            CreateOrder.ConsiderationItem(
                startAmount = offererAmount,
                endAmount = offererAmount,
                recipient = wallet.address
            )
        )
        considerations.add(
            CreateOrder.ConsiderationItem(
                startAmount = marketAmount,
                endAmount = marketAmount,
                recipient = collection.marketFeeAddress!!
            )
        )
        if (collectionAmount > BigInteger.ZERO) {
            collectionFees.forEach { fee ->
                val feeValue = totalAmountValue * fee.value
                considerations.add(
                    CreateOrder.ConsiderationItem(
                        startAmount = feeValue,
                        endAmount = feeValue,
                        recipient = fee.key
                    )
                )
            }
        }
        val order = CreateOrder.Order(
            orderType = 0,
            offerer = wallet.address,
            startTime = startDate.epochSecond.toBigInteger(),
            endTime = endDate.epochSecond.toBigInteger(),
            zone = getZone(),
            zoneHash = getZoneHash(),
            conduitKey = getConduitKey(),
            salt = HashUtils.generateRandomNumericSalt(),
            offer = listOf(
                CreateOrder.OfferItem(
                    itemType = 2,
                    token = collectionAddress,
                    identifierOrCriteria = asset.uid.toBigInteger(),
                    startAmount = BigInteger.ONE,
                    endAmount = BigInteger.ONE
                )
            ),
            counter = counter,
            consideration = considerations
        )
        val message = CreateOrder.createMessage(
            order = order,
            salt = HashUtils.generateRandomSalt(),
            verifyingContract = protocolAddress,
        )
        val messageEncoder = StructuredDataEncoder(message)
        val messageBytes = messageEncoder.hashStructuredData()
        val messageKeyPair = WalletMapper.toECKeyPair(wallet)
        val messageSignature = Sign.signMessage(messageBytes, messageKeyPair, false)

        val request = CreateOrder.Listing(
            parameters = order,
            protocolAddress = protocolAddress,
            signature = HashUtils.signatureDataToString(messageSignature)
        )

        // list order
        return httpKeysRotator.rotate { key ->
            postLimiter.limit(key) {
                ktor
                    .post {
                        header("X-API-KEY", key)
                        url {
                            url("${api2Url}/orders/${chain.chain}/seaport/listings")
                        }
                        setBody(request)
                    }
                    .assertError()
                    .body<Unit>()
            }
        }
    }

    override suspend fun fulfillListing(listing: Listing, wallet: Wallet): UserTx {
        val request = FulfillListing.Request(
            listing = FulfillListing.Listing(
                hash = listing.hash,
                chain = listing.chain.chain,
                protocolAddress = listing.protocolAddress
            ),
            fulfiller = FulfillListing.Fulfiller(
                address = wallet.address
            )
        )
        val response = httpKeysRotator.rotate { key ->
            postLimiter.limit(key) {
                ktor
                    .post {
                        header("X-API-KEY", key)
                        url {
                            url("${api2Url}/listings/fulfillment_data")
                        }
                        setBody(request)
                    }
                    .assertError()
                    .body<FulfillListing.Response>()
            }
        }
        val transaction = response.data.transaction
        val data = TransactionData(
            params = transaction.inputData.parameters,
            methodSignature = transaction.function,
            value = transaction.value,
            to = transaction.to,
            from = wallet,
        )
        return TransactionConstructor().construct(data)
    }

    private suspend fun getAssets(
        contractAddress: String,
        walletAddress: String,
        cursor: String? = null
    ): GetAssets.Response {
        return httpKeysRotator.rotate { key ->
            getLimiter.limit(key) {
                ktor
                    .get {
                        header("X-API-KEY", key)
                        url {
                            url("${api1Url}/assets")
                            parameter("limit", 200)
                            parameter("owner", walletAddress)
                            parameter("asset_contract_address", contractAddress)
                            if (cursor != null) {
                                parameter("cursor", cursor)
                            }
                        }
                    }
                    .assertRateLimited()
                    .body()
            }
        }
    }

    data class CollectionStatKey(
        val slug: String,
        override val ttl: Long = ICacheKey.TTL_10_SECONDS
    ) : ICacheKey<CollectionStat>

}