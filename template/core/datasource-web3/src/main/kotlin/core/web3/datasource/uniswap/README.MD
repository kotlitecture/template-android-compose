# Links
https://thegraph.com/hosted-service/subgraph/uniswap/uniswap-v3
https://docs.uniswap.org/api/subgraph/guides/examples
https://github.com/Uniswap/v3-subgraph/blob/main/schema.graphql

# Last 100 candles

{
    pool(id: "0x2f62f2b4c5fcd7570a709dec05d68ea19c82a9ec") {
        id
        poolHourData(
            orderBy: periodStartUnix,
            orderDirection: desc,
            first: 100
        ) {
            open
            close
            high
            low
            feeGrowthGlobal0X128
            feeGrowthGlobal1X128
            liquidity
            volumeUSD
        }
    }
}

# Swap activity

{
swaps(
first: 200,
orderBy: timestamp,
orderDirection: desc,
where: {
token1_: { symbol: "WETH" },
timestamp_gt: $timestamp
}) {
pool {
token0 {
id
symbol
}
token1 {
id
symbol
}
liquidity
feeTier
createdAtTimestamp
sqrtPrice
token0Price
token1Price
txCount
id
totalValueLockedETH
totalValueLockedUSD
volumeUSD
}
sender
recipient
amount0
amount1
id
timestamp
logIndex
}
}

# Latest created pools

{
pools(
orderBy: createdAtTimestamp, 
orderDirection: desc,
where: {
token1_: { symbol: "WETH" },
createdAtTimestamp_gt: $timestamp
}) {
id
token0 {
id
symbol
}
token1 {
id
symbol
}
liquidity
feeTier
createdAtTimestamp
sqrtPrice
token0Price
token1Price
txCount
totalValueLockedETH
totalValueLockedUSD
volumeUSD
}
}