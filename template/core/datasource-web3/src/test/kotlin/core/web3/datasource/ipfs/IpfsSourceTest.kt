package core.web3.datasource.ipfs

import core.web3.AbstractTest
import org.tinylog.kotlin.Logger
import kotlin.test.Test

class IpfsSourceTest : AbstractTest() {

    private val api: IpfsSource = context.getIpfsSource()

    @Test
    fun `read catalog sorted by file size`() = performTest {
//        val catalogId = "QmdxDAXQZn7LtbTCrfLgYX2YbWHGi2t7UQwqSEpt45YEh2" // goblintown
        val catalogId = "bafybeif7fypfqpqufa6tc5gvpxw5zow3mcahmcr4mi2peicm55t5cxotmm"
        val files = api.getFiles(catalogId)
        files.take(20).onEach { file ->
            Logger.debug("file :: id={}, size={}", file.name, file.size)
        }
    }

}