package core.web3

import core.essentials.cache.ICacheSource
import core.essentials.cache.impl.MemoryCacheSource
import core.essentials.http.HttpSource
import core.web3.datasource.alchemy.IAlchemySource
import core.web3.datasource.alchemy.impl.AlchemySource
import core.web3.datasource.blocknative.IBlocknativeSource
import core.web3.datasource.blocknative.impl.BlocknativeSource
import core.web3.datasource.coingecko.ICoinGeckoSource
import core.web3.datasource.coingecko.impl.CoinGeckoSource
import core.web3.interactor.eth.IEthereumInteractor
import core.web3.interactor.eth.contract.impl.EthereumInteractor
import core.web3.datasource.ethereum.IEthereumSource
import core.web3.datasource.ethereum.impl.EthereumSource
import core.web3.datasource.etherscan.IEtherscanSource
import core.web3.datasource.etherscan.impl.EtherscanSource
import core.web3.datasource.ipfs.IpfsSource
import core.web3.datasource.ipfs.impl.IpfsSourceImpl
import core.web3.datasource.opensea.IOpenSeaSource
import core.web3.datasource.opensea.impl.OpenSeaSource
import core.web3.datasource.uniswap.IUniswapSource
import core.web3.datasource.uniswap.v2.UniswapV2Source
import core.web3.datasource.uniswap.v3.UniswapV3Source
import core.web3.model.Blockchain
import core.web3.model.Wallet

data class TestWeb3Context(
    private val pollInterval: Long = 12_000L,
    private val ipfsGatewayUrl: String = "http://ipfs.io",
    private val openSeaHttpKeys:List<String> = listOf("0185bf7171e742ecaf6478710b66f0b0", "4e7be6e60d8f40f4834cf53a31f1dca8"),
    private val openSeaWsKeys:List<String> = listOf("2e3f5816375d4732b96a4e2b13f94fe3"),
    private val alchemyApiKey: String = "gpTM7TZBPB0m6aKIIZGX7GP-1SoNlYC3",
    private val etherscanApiKey: String = "NUMY2GSKV23ZGT9HNR9I6BA6NG3DYFCGZD",
    private val blocknativeApiKey: String = "4ea7b719-7114-4966-8fa6-25ab5c5a2f88",
    private val mintNodeUrl: String = "https://mainnet.infura.io/v3/8b7b878f6ffc4dfda4867b22275134b5",
    private val metadataNodeUrl: String = "https://mainnet.infura.io/v3/8b7b878f6ffc4dfda4867b22275134b5"
) : IWeb3Context {

    private val httpSource = HttpSource()
    private val cacheSource = MemoryCacheSource()
    private val ipfsSource = IpfsSourceImpl(httpSource, ipfsGatewayUrl)
    private val ethWalletLazy by lazy { Blockchain.ETH.generateWallet() }
    private val alchemySource = AlchemySource(alchemyApiKey, httpSource, cacheSource)
    private val etherscanSource = EtherscanSource(cacheSource, httpSource, etherscanApiKey)
    private val ethereumSource = EthereumSource(cacheSource, httpSource, mintNodeUrl, pollInterval)
    private val blocknativeSource = BlocknativeSource(blocknativeApiKey, httpSource)
    private val coinGeckoSource = CoinGeckoSource(cacheSource, httpSource)
    private val ethereumInteractor = EthereumInteractor(this, metadataNodeUrl)
    private val openSeaSource = OpenSeaSource(cacheSource, httpSource, ethereumInteractor, openSeaHttpKeys, openSeaWsKeys)
    private val uniswapV3Source = UniswapV3Source(httpSource, pollInterval)
    private val uniswapV2Source = UniswapV2Source(httpSource, pollInterval)

    override fun getEthWallet(): Wallet = ethWalletLazy
    override fun getIpfsSource(): IpfsSource = ipfsSource
    override fun getHttpSource(): HttpSource = httpSource
    override fun getCacheSource(): ICacheSource = cacheSource
    override fun getOpenSeaSource(): IOpenSeaSource = openSeaSource
    override fun getAlchemySource(): IAlchemySource = alchemySource
    override fun getEthereumSource(): IEthereumSource = ethereumSource
    override fun getEtherscanSource(): IEtherscanSource = etherscanSource
    override fun getCoinGeckoSource(): ICoinGeckoSource = coinGeckoSource
    override fun getBlocknativeSource(): IBlocknativeSource = blocknativeSource
    override fun getEthereumInteractor(): IEthereumInteractor = ethereumInteractor
    override fun getUniswapV3Source(): IUniswapSource = uniswapV3Source
    override fun getUniswapV2Source(): IUniswapSource = uniswapV2Source

}