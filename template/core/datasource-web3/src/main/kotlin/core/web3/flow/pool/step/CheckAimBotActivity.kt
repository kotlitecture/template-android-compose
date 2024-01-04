package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.essentials.misc.extensions.asBigDecimal
import core.web3.flow.pool.PoolFlowContext

class CheckAimBotActivity : FlowStep<PoolFlowContext>() {

    override suspend fun doProceed(context: PoolFlowContext) {
        val earliest = context.poolEarliestTransactions
        val addresses = listOf(
            "0x116d1edd539e5e93551973eb2a71898c9095122f".lowercase(),
            "0x50b8f49f4b2e80e09ce8015c4e7a9c277738fd3d".lowercase()
        )
        val transactions = earliest.filter { addresses.contains(it.sender.lowercase()) }
        val sales = transactions.filter { it.isSale() }
        val buys = transactions.filter { it.isBuy() }
        when {
            buys.isEmpty() && sales.isEmpty() -> {
                context.aimBotBuyPosition = 0
            }

            else -> {
                context.aimBotBuyPosition = earliest.indexOf(buys.first())
                context.aimBotSalePosition = earliest.indexOf(sales.first())
                if (sales.isNotEmpty()) {
                    context.aimBotProfit =
                        sales.sumOf { it.amount1 }.abs().minus(buys.sumOf { it.amount1 })
                            .asBigDecimal(5)
                }
            }
        }
    }

}