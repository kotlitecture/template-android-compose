package core.web3.command.eth.contract

import core.web3.AbstractTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.tinylog.kotlin.Logger

class GetMintByHashTest : AbstractTest() {

    @Test
    fun `get mint by hash`() = performTest {
        val hash = "0x195344e7067e49dd76c7407ab352ca52b96dc2feeb6e6f1cbc21567678f0f737"
        val mint = GetMintByHash(hash).execute(context)
        Logger.debug(mint)
        Assertions.assertEquals(2, mint.tokens.size)
        Assertions.assertEquals(listOf(46, 47), mint.tokens.map { it.toInt() })
    }

    @Test
    fun `get collection by contract address from mint`() = performTest {
        val hash = "0x042bbccb503518dac27a1373d90944828900b7b8b51f83dba85efd3b6ce93112"
        val mint = GetMintByHash(hash).execute(context)
        val collection = context.getOpenSeaSource().getByAddress(mint.collectionAddress)
        Assertions.assertNotNull(collection)
    }

}