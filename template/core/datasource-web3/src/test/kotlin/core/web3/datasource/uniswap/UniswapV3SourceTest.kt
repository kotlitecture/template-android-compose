package core.web3.datasource.uniswap

class UniswapV3SourceTest : UniswapSourceTest() {

    override val api: IUniswapSource = context.getUniswapV3Source()

    override val poolId: String = "0x8ad599c3a0ff1de082011efddc58f1908eb6e6d8"

}