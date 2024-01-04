package core.web3.interactor.eth.contract

import core.web3.AbstractTest
import core.web3.model.Wallet
import core.web3.model.eth.TransferEvent
import org.junit.jupiter.api.Assertions
import org.tinylog.Logger
import java.math.BigInteger
import kotlin.test.Test

class Erc721Test : AbstractTest() {

    private fun interactor(address: String): IErc721 {
        return context.getEthereumInteractor().contractErc721(context.getEthWallet(), address)
    }

    @Test
    fun `get collection info`() = performTest {
        val contract = interactor(CONTRACT_BATTLESHIPZ)
        val info = contract.getAssetContract()
        Assertions.assertEquals(10000, info.maxSupply!!.toInt())
        Assertions.assertEquals("Battleshipz", info.name)
        Assertions.assertEquals("SHIPZ", info.symbol)
    }

    @Test
    fun `get token meta of wrong contract`() = performTest {
        val token = createToken(1, "0xd7637649eb780dcebbbdd11e33a70c5d02b0430f")
        val asset = interactor(token.address).getAsset(token.id)
        Assertions.assertNull(asset)
    }

    @Test
    fun `get token meta of not existing token`() = performTest {
        val token = createToken(-1, "0xd7637649eb780dcebbbdd11e33a70c5d02b04302")
        val asset = interactor(token.address).getAsset(token.id)
        Assertions.assertNull(asset)
    }

    @Test
    fun `get meta of base64 token`() = performTest {
        val token = createToken(1, "0xd7637649eb780dcebbbdd11e33a70c5d02b04302")
        val meta = interactor(token.address).getAsset(token.id)!!
        Logger.debug("meta :: {}", meta)
        Assertions.assertTrue(meta.attrs.isNotEmpty())
    }

    @Test
    fun `get meta of ipfs token`() = performTest {
        val token = createToken(1, "0x3c6fbc94288f5af5201085948ddb18aded2e6879")
        val asset = interactor(token.address).getAsset(token.id)!!
        Logger.debug("meta :: {}", asset)
        Assertions.assertTrue(asset.attrs.isNotEmpty())
    }

    @Test
    fun `get collection max supply`() = performTest {
        val address = "0x3c6fbc94288f5af5201085948ddb18aded2e6879"
        val contract = interactor(address)
        val supply = contract.maxSupply()!!
        Assertions.assertEquals(10000.toBigInteger(), supply)
    }

    @Test
    fun `get collection total supply`() = performTest {
        val address = "0x3c6fbc94288f5af5201085948ddb18aded2e6879"
        val contract = interactor(address)
        val supply = contract.totalSupply()!!
        Assertions.assertEquals(10000.toBigInteger(), supply)
    }

    @Test
    fun `get collection name`() = performTest {
        val address = "0xd7637649eb780dcebbbdd11e33a70c5d02b04302"
        val contract = interactor(address)
        val name = contract.name()
        Assertions.assertEquals("Battleshipz", name)
    }

    @Test
    fun `get collection symbol`() = performTest {
        val address = "0xd7637649eb780dcebbbdd11e33a70c5d02b04302"
        val contract = interactor(address)
        val symbol = contract.symbol()
        Assertions.assertEquals("SHIPZ", symbol)
    }

    @Test
    fun `get contract implementation`() = performTest {
        val proxy = "0x4ebb2384cc1e86f578e37f2057b336b9027cb95a"
        val address = interactor(proxy).getImpl()?.contractAddress
        Assertions.assertEquals("0x419074d73cf0852e46b8531b430b1230c348c291", address)
    }

    @Test
    fun `get balance of`() = performTest {
        val contract = "0xd7637649eb780dcebbbdd11e33a70c5d02b04302"
        val address = "0x905BB5B5B1Fb3101fD76Da5B821B982012325C77"
        val contractRef = interactor(contract)
        val balanceOf = contractRef.balanceOf(address)!!
        Assertions.assertTrue(balanceOf > BigInteger.ZERO)
    }

    private fun createToken(id: Int, contractAddress: String): TransferEvent = TransferEvent(
        to = "",
        from = "",
        transactionHash = "",
        address = contractAddress,
        id = id.toBigInteger(),
        blockNumber = BigInteger.ZERO
    )

}