package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.essentials.misc.extensions.unixToDate
import core.web3.datasource.uniswap.model.Direction
import core.web3.flow.pool.PoolFlowContext
import java.math.BigInteger

class FetchPoolTransactions : FlowStep<PoolFlowContext>() {

    override suspend fun doProceed(context: PoolFlowContext) {
        val latestLimit = 100
        val earliestLimit = 500
        val txCount = context.pool.txCount ?: BigInteger.ZERO
        val api = context.web3.getUniswapSource(context.platform) ?: return
        val latest = api.getSwaps(context.pool.id, latestLimit, Direction.Latest)
        val earliest = api.getSwaps(context.pool.id, earliestLimit, Direction.Earliest)
        context.poolTransactionsCount = maxOf(txCount, earliest.size.toBigInteger())
        context.poolLastTransactionDate = latest.firstOrNull()?.timestamp?.unixToDate()
        context.poolEarliestTransactions = earliest
        context.poolLatestTransactions = latest
    }

}