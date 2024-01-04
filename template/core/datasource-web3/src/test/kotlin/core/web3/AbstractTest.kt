package core.web3

import core.web3.model.Blockchain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.tinylog.kotlin.Logger
import kotlin.system.measureTimeMillis

abstract class AbstractTest {

    protected val context: IWeb3Context = TestWeb3Context()

    protected val ethMintWallet by lazy { Blockchain.ETH.createWallet("671f61a7d3aaf7425061cc73767189cc2336533a2a345b12dd6cefea3b1a73f4")!! }

    protected fun performTest(block: suspend CoroutineScope.() -> Unit) {
        runBlocking {
            val time = measureTimeMillis { block() }
            Logger.debug("test duration :: {}", time)
        }
    }

    companion object {
        const val CONTRACT_BATTLESHIPZ = "0xd7637649eb780dcebbbdd11e33a70c5d02b04302"
        const val CONTRACT_GOBLINTOWN = "0x8c6def540b83471664edc6d5cf75883986932674"
        const val CONTRACT_IDK = "0xc589770757cd0d372c54568bf7e5e1d56b958015"
    }

}