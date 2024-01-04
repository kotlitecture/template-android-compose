package core.web3

import core.essentials.cache.ICacheSource
import core.essentials.http.HttpSource
import core.web3.datasource.alchemy.IAlchemySource
import core.web3.datasource.blocknative.IBlocknativeSource
import core.web3.datasource.coingecko.ICoinGeckoSource
import core.web3.datasource.ethereum.IEthereumSource
import core.web3.datasource.etherscan.IEtherscanSource
import core.web3.datasource.ipfs.IpfsSource
import core.web3.datasource.opensea.IOpenSeaSource
import core.web3.datasource.uniswap.IUniswapSource
import core.web3.interactor.eth.IEthereumInteractor
import core.web3.model.Platform
import core.web3.model.Wallet

interface IWeb3Context {

    fun getEthWallet(): Wallet

    fun getHttpSource(): HttpSource

    fun getIpfsSource(): IpfsSource

    fun getCacheSource(): ICacheSource

    fun getOpenSeaSource(): IOpenSeaSource

    fun getEthereumSource(): IEthereumSource

    fun getEtherscanSource(): IEtherscanSource

    fun getAlchemySource(): IAlchemySource

    fun getCoinGeckoSource(): ICoinGeckoSource

    fun getBlocknativeSource(): IBlocknativeSource

    fun getEthereumInteractor(): IEthereumInteractor

    fun getUniswapV3Source(): IUniswapSource

    fun getUniswapV2Source(): IUniswapSource

    fun getUniswapSource(platform: Platform): IUniswapSource? = when (platform) {
        Platform.UniSwapV2 -> getUniswapV2Source()
        Platform.UniSwapV3 -> getUniswapV3Source()
        else -> null
    }

}