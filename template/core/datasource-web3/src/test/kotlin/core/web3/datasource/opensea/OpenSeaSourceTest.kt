package core.web3.datasource.opensea

import core.web3.AbstractTest
import core.web3.datasource.ethereum.impl.EthereumTransaction
import core.web3.datasource.opensea.model.ItemSoldEvent
import core.web3.datasource.opensea.model.Listing
import core.web3.datasource.opensea.model.Order
import core.web3.datasource.opensea.model.StreamEvent
import core.web3.datasource.opensea.model.StreamFilter
import core.web3.datasource.opensea.model.StreamType
import core.web3.extensions.weiToEth
import core.web3.model.Asset
import core.web3.model.Blockchain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.take
import org.junit.jupiter.api.Assertions
import org.tinylog.Logger
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import kotlin.test.Ignore
import kotlin.test.Test

class OpenSeaSourceTest : AbstractTest() {

    private val api: IOpenSeaSource = context.getOpenSeaSource()

    @Test
    fun `get assets`() = performTest {
        val address = "0x895688bf87d73cc7da27852221799d31b027e300".lowercase()
        val wallet = "0x905BB5B5B1Fb3101fD76Da5B821B982012325C77".lowercase()
        val assets = api.getAssets(address, wallet)
        Assertions.assertTrue(assets.isNotEmpty())
        Assertions.assertEquals(11, assets.size)
        assets.forEach { asset ->
            Logger.debug("\n\nasset :: {}", asset)
            Assertions.assertEquals(asset.address, address)
            Assertions.assertEquals(asset.wallet, wallet)
            Assertions.assertNotNull(asset.attrs[Asset.ATTR_TRAITS])
            Assertions.assertNotNull(asset.attrs[Asset.ATTR_COLLECTION_NAME])
            Assertions.assertNotNull(asset.attrs[Asset.ATTR_COLLECTION_SLUG])
        }
    }

    @Test
    fun `get assets by keys with different collections`() = performTest {
        val keys = listOf(
            Asset.AssetKey("10568", "0x76be3b62873462d2142405439777e971754e8e77".lowercase()),
            Asset.AssetKey("3717", "0x60e4d786628fea6478f785a6d7e704777c86a7c6".lowercase()),
            Asset.AssetKey("4503", "0x9b1750e5e27b491f2e3b3fa969e11a41f4ebb550".lowercase()),
        )
        val assets = api.getAssets(keys)
            .sortedBy { asset -> keys.indexOfFirst { it.id == asset.uid } }
        Assertions.assertEquals(keys.size, assets.size)
        assets.forEachIndexed { index, asset ->
            Assertions.assertEquals(keys[index].id, asset.uid)
            Assertions.assertEquals(keys[index].address, asset.address)
            Assertions.assertNotNull(asset.attrs[Asset.ATTR_TRAITS])
            Assertions.assertNotNull(asset.attrs[Asset.ATTR_COLLECTION_NAME])
            Assertions.assertNotNull(asset.attrs[Asset.ATTR_COLLECTION_SLUG])
        }
    }

    @Test
    fun `get assets by keys with same collection`() = performTest {
        val keys = listOf(
            Asset.AssetKey("3717", "0x60e4d786628fea6478f785a6d7e704777c86a7c6".lowercase()),
            Asset.AssetKey("3716", "0x60e4d786628fea6478f785a6d7e704777c86a7c6".lowercase())
        )
        val assets = api.getAssets(keys)
            .sortedBy { asset -> keys.indexOfFirst { it.id == asset.uid } }
        Assertions.assertEquals(keys.size, assets.size)
        assets.forEachIndexed { index, asset ->
            Assertions.assertEquals(keys[index].id, asset.uid)
            Assertions.assertEquals(keys[index].address, asset.address)
            Assertions.assertNotNull(asset.attrs[Asset.ATTR_TRAITS])
            Assertions.assertNotNull(asset.attrs[Asset.ATTR_COLLECTION_NAME])
            Assertions.assertNotNull(asset.attrs[Asset.ATTR_COLLECTION_SLUG])
        }
    }

    @Test
    fun `get collection by existing address`() = performTest {
        val address = CONTRACT_GOBLINTOWN
        val meta = api.getByAddress(address)!!
        Assertions.assertEquals("goblintownwtf", meta.slug)
        Assertions.assertNotNull(meta.createDate)
        Assertions.assertEquals(Blockchain.ETH, meta.blockchain)
    }

    @Test
    fun `get all collection listings`() = performTest {
        val slug = "goblintownwtf"
        val orders = mutableListOf<Order>()
        var next: String? = null
        while (true) {
            val response = api.getListings(Order.RequestAll(slug = slug, next = next))
            next = response.next
            orders.addAll(response.orders!!)
            if (response.orders!!.isEmpty() || next == null) {
                break
            } else {
                Logger.debug("request next page :: {}", next)
            }
        }
        Assertions.assertTrue(orders.isNotEmpty())
        orders.sortBy { it.price!!.currentPrice.value }
        Logger.debug("all listings :: {}", orders.size)
        Logger.debug("min price :: {}", orders.first().price!!.currentPrice.value.weiToEth())
        Logger.debug("max price :: {}", orders.last().price!!.currentPrice.value.weiToEth())
    }

    @Test
    fun `get specific collection listings`() = performTest {
        val slug = "goblintownwtf"
        val collection = api.getBySlug(slug)!!
        val listings = api.getListings(Order.RequestAll(slug = slug))
        val tokens = listings.orders!!
            .filter { it.isListing() }
            .mapNotNull { it.getTokenId() }.take(20)
        val updated = api.getListings(
            Order.RequestSpecific(
                address = collection.address,
                chain = collection.blockchain,
                tokens = tokens
            )
        )
        Assertions.assertEquals(tokens.size, updated.size)
        Logger.debug("updated :: {} -> {}", tokens, updated.map { it.getTokenId() })
        updated.forEach { order ->
            Assertions.assertTrue(tokens.contains(order.getTokenId()))
            Logger.debug(
                "order :: {} - {} - {} - {} - {}",
                order.getTokenId(),
                order.cancelled,
                order.finalized,
                order.invalid,
                order.type
            )
            Assertions.assertTrue(order.isListing())
            Assertions.assertNotNull(order.getTokenIconUrl())
        }

        updated.forEach { tokenOrder ->
            val tokenOrderAfter = api
                .getListings(
                    Order.RequestSpecific(
                        address = collection.address,
                        chain = collection.blockchain,
                        tokens = listOf(tokenOrder.getTokenId()!!)
                    )
                )
                .first()
            Assertions.assertEquals(tokenOrder.getOrderPrice(), tokenOrderAfter.getOrderPrice())
        }
    }

    @Test
    fun `get collection by slug`() = performTest {
        val slug = "azuki"
        val meta = api.getBySlug(slug)!!
        Assertions.assertEquals(slug, meta.slug)
        Assertions.assertNotNull(meta.createDate)
        Assertions.assertEquals(Blockchain.ETH, meta.blockchain)
    }

    @Test
    fun `get ItemSold events`() = performTest {
        getEvents(StreamType.ItemSold)
            .map { it as ItemSoldEvent }
            .mapNotNull { it.payload }
            .mapNotNull { it.payload }
            .take(1)
            .collect {
                Logger.debug(
                    "item :: slug={}, id={}, price={} {}, taker={}, meta={}",
                    it.collection.slug,
                    it.item.getTokenId(),
                    it.salePrice.weiToEth(),
                    it.token.symbol,
                    it.taker.address,
                    it.item.metadata,
                )
            }
    }

    @Test
    fun `get ItemListed events`() = performTest {
        getEvents(StreamType.ItemListed)
            .take(1)
            .collect {
                Assertions.assertTrue(!it.getProtocolAddress().isNullOrBlank())
                Assertions.assertTrue(!it.getOrderHash().isNullOrBlank())
            }
    }

    @Test
    @Ignore
    fun `get ItemTransferred events`() = performTest {
        getEvents(StreamType.ItemTransferred)
            .take(1)
            .collect {}
    }

    @Test
    @Ignore
    fun `get CollectionOffer events`() = performTest {
        getEvents(StreamType.CollectionOffer)
            .take(1)
            .collect {}
    }

    @Test
    @Ignore
    fun `get ItemCancelled events`() = performTest {
        getEvents(StreamType.ItemCancelled)
            .take(1)
            .collect {}
    }

    @Test
    fun `get collection stat`() = performTest {
        val slug = "goblintownwtf"
        val stat = api.getStat(slug)
        Assertions.assertNotNull(stat)
        Logger.debug("stat :: {}", stat)
    }

    @Test
    fun `fulfill listing`() = performTest {
        val orders = api
            .getListings(
                Order.RequestAll(
                    slug = "battleshipz"
                )
            )
            .orders!!
            .filter { it.isListing() }
            .sortedBy { it.getOrderPrice() }
        val order = orders.first()
        val wallet =
            Blockchain.ETH.createWallet("684759e4cae4f5a9439ba6e2a7e380dde8c9d6bfbbcb8681906c60a056f96ccc")!!
        val listing = Listing(
            hash = order.orderHash,
            chain = Blockchain.ETH,
            protocolAddress = order.protocolAddress
        )
        val userTx = api.fulfillListing(listing, wallet)
        val transaction = EthereumTransaction(userTx, context)
        Logger.debug("cost :: {}", transaction.getTransactionFee())
    }

    @Test
    @Ignore
    fun `create listing`() = performTest {
        val contract = "0x895688bf87d73cc7da27852221799d31b027e300"
        val wallet =
            Blockchain.ETH.createWallet("684759e4cae4f5a9439ba6e2a7e380dde8c9d6bfbbcb8681906c60a056f96ccc")!!
        val assets = api.getAssets(contract, wallet.address)
        Assertions.assertTrue(assets.isNotEmpty())
        val asset = assets.last()
        api.createListing(wallet, asset, BigDecimal("0.4"), TimeUnit.MINUTES.toMillis(30))
    }

    private suspend fun getEvents(type: StreamType): Flow<StreamEvent<*>> {
        val events = listOf(type)
        val chains = listOf(Blockchain.ETH)
        val slug = StreamFilter.COLLECTION_ANY
        return api.getEvents(StreamFilter(slug, events, chains))
    }

}