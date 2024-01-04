package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.web3.flow.pool.PoolFlowContext

class CheckPoolNamedCorrectly : FlowStep<PoolFlowContext>() {

    private val symbols = "1234567890abcdefghijklmnopqrstuvwxyz$-+&".toList()
    private val ignoredNames = setOf(
        "pepe",
        "milk",
        "fiver",
        "bitcoin",
        "doge",
    )

    override suspend fun doProceed(context: PoolFlowContext) {
        val symbol = context.pool.token0.symbol.lowercase()
        if (!context.contractIsIncorrect) {
            context.contractIsIncorrect = !symbol.all { it in symbols }
        }
        if (!context.contractIsIncorrect) {
            context.contractIsIncorrect = ignoredNames.any { symbol.contains(it) }
        }
    }

}