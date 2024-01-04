package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.essentials.misc.extensions.equalsZero
import core.web3.flow.pool.PoolFlowContext

class CheckPoolSuspicious : FlowStep<PoolFlowContext>() {

    override suspend fun doProceed(context: PoolFlowContext) {
        val transactions = context.poolLatestTransactions
        if (!context.contractIsSuspicious && transactions.isNotEmpty()) {
            log("CheckPoolSuspicious :: {}", "2")
            context.contractIsSuspicious = transactions.isEmpty().also { log("CheckPoolSuspicious :: {}", "2.1") }
                || transactions.all { it.isBuy() }.also { log("CheckPoolSuspicious :: {}", "2.2") }
                || transactions.filter { it.isSale() }.size >= transactions.filter { it.isBuy() }.size.also { log("CheckPoolSuspicious :: {}", "2.3") }
                || transactions.takeLast(5).let { it.size == 5 && it.all { it.isSale() } }.also { log("CheckPoolSuspicious :: {}", "2.4") }
                || (transactions.groupBy { it.sender }
                .filter { group -> group.value.size > 1 }
                .filter {
                    val buys = it.value.filter { it.isBuy() }.map { it.amount1 }
                    val sells = it.value.filter { it.isSale() }.map { it.amount1 }
                    val sumOfBuys = buys.sumOf { it }.abs()
                    val sumOfSells = sells.sumOf { it }.abs()
                    log("CheckPoolSuspicious :: buy-sell ratio {}/{}", sumOfBuys, sumOfSells)
                    buys.isNotEmpty() && sells.isNotEmpty() && sumOfBuys > sumOfSells
                }
                .size > 1
                ).also { log("CheckPoolSuspicious :: {}", "2.5") }
        }
        if (!context.contractIsSuspicious) {
            log("CheckPoolSuspicious :: {}", "3")
            context.contractIsSuspicious = context.poolLiquidity.equalsZero()
        }
    }

}