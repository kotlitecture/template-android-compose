package core.web3.datasource.uniswap

import kotlin.test.Ignore

class UniswapV2SourceTest : UniswapSourceTest() {

    override val api: IUniswapSource = context.getUniswapV2Source()

    override val poolId: String = "0xd425fe81210028f565f7e3830769409f7d8c2c2e"

    @Ignore
    override fun `get candles`() = Unit

}