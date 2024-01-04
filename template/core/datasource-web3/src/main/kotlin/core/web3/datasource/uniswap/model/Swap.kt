package core.web3.datasource.uniswap.model

import java.math.BigDecimal
import java.math.BigInteger

data class Swap(
    val id: String,
    val pool: Pool,
    val transaction: Transaction,
    val timestamp: BigInteger,
    val amount0: BigDecimal,
    val amount1: BigDecimal,
    val recipient: String,
    val sender: String,
) {
    fun isBuy(): Boolean = amount1 > BigDecimal.ZERO
    fun isSale(): Boolean = amount1 < BigDecimal.ZERO

    fun normalize(): Swap {
        if (pool.token0.isWeth()) {
            return copy(
                pool = pool.normalize(),
                amount0 = amount1,
                amount1 = amount0
            )
        }
        return this
    }
}
