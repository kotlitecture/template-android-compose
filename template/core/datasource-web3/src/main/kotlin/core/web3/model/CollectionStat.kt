package core.web3.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CollectionStat(
    @SerializedName("one_minute_volume")
    val oneMinuteVolume: BigDecimal,
    @SerializedName("one_minute_change")
    val oneMinuteChange: Float,
    @SerializedName("one_minute_sales")
    val oneMinuteSales: Int,
    @SerializedName("one_minute_sales_change")
    val oneMinuteSalesChange: Float,
    @SerializedName("one_minute_average_price")
    val oneMinuteAveragePrice: BigDecimal,
    @SerializedName("one_minute_difference")
    val oneMinuteDifference: Float,
    @SerializedName("five_minute_volume")
    val fiveMinuteVolume: BigDecimal,
    @SerializedName("five_minute_change")
    val fiveMinuteChange: Float,
    @SerializedName("five_minute_sales")
    val fiveMinuteSales: Int,
    @SerializedName("five_minute_sales_change")
    val fiveMinuteSalesChange: Float,
    @SerializedName("five_minute_average_price")
    val fiveMinuteAveragePrice: BigDecimal,
    @SerializedName("five_minute_difference")
    val fiveMinuteDifference: Float,
    @SerializedName("fifteen_minute_volume")
    val fifteenMinuteVolume: BigDecimal,
    @SerializedName("fifteen_minute_change")
    val fifteenMinuteChange: Float,
    @SerializedName("fifteen_minute_sales")
    val fifteenMinuteSales: Int,
    @SerializedName("fifteen_minute_sales_change")
    val fifteenMinuteSalesChange: Float,
    @SerializedName("fifteen_minute_average_price")
    val fifteenMinuteAveragePrice: BigDecimal,
    @SerializedName("fifteen_minute_difference")
    val fifteenMinuteDifference: Float,
    @SerializedName("thirty_minute_volume")
    val thirtyMinuteVolume: BigDecimal,
    @SerializedName("thirty_minute_change")
    val thirtyMinuteChange: Float,
    @SerializedName("thirty_minute_sales")
    val thirtyMinuteSales: Int,
    @SerializedName("thirty_minute_sales_change")
    val thirtyMinuteSalesChange: Float,
    @SerializedName("thirty_minute_average_price")
    val thirtyMinuteAveragePrice: BigDecimal,
    @SerializedName("thirty_minute_difference")
    val thirtyMinuteDifference: Float,
    @SerializedName("one_hour_volume")
    val oneHourVolume: BigDecimal,
    @SerializedName("one_hour_change")
    val oneHourChange: Float,
    @SerializedName("one_hour_sales")
    val oneHourSales: Int,
    @SerializedName("one_hour_sales_change")
    val oneHourSalesChange: Float,
    @SerializedName("one_hour_average_price")
    val oneHourAveragePrice: BigDecimal,
    @SerializedName("one_hour_difference")
    val oneHourDifference: Float,
    @SerializedName("six_hour_volume")
    val sixHourVolume: BigDecimal,
    @SerializedName("six_hour_change")
    val sixHourChange: Float,
    @SerializedName("six_hour_sales")
    val sixHourSales: Int,
    @SerializedName("six_hour_sales_change")
    val sixHourSalesChange: Float,
    @SerializedName("six_hour_average_price")
    val sixHourAveragePrice: BigDecimal,
    @SerializedName("six_hour_difference")
    val sixHourDifference: Float,
    @SerializedName("one_day_volume")
    val oneDayVolume: BigDecimal,
    @SerializedName("one_day_change")
    val oneDayChange: Float,
    @SerializedName("one_day_sales")
    val oneDaySales: Int,
    @SerializedName("one_day_sales_change")
    val oneDaySalesChange: Float,
    @SerializedName("one_day_average_price")
    val oneDayAveragePrice: BigDecimal,
    @SerializedName("one_day_difference")
    val oneDayDifference: Float,
    @SerializedName("seven_day_volume")
    val sevenDayVolume: BigDecimal,
    @SerializedName("seven_day_change")
    val sevenDayChange: Float,
    @SerializedName("seven_day_sales")
    val sevenDaySales: Int,
    @SerializedName("seven_day_sales_change")
    val sevenDaySalesChange: Float,
    @SerializedName("seven_day_average_price")
    val sevenDayAveragePrice: BigDecimal,
    @SerializedName("seven_day_difference")
    val sevenDayDifference: Float,
    @SerializedName("thirty_day_volume")
    val thirtyDayVolume: BigDecimal,
    @SerializedName("thirty_day_change")
    val thirtyDayChange: Float,
    @SerializedName("thirty_day_sales")
    val thirtyDaySales: Int,
    @SerializedName("thirty_day_average_price")
    val thirtyDayAveragePrice: BigDecimal,
    @SerializedName("thirty_day_difference")
    val thirtyDayDifference: Float,
    @SerializedName("total_volume")
    val totalVolume: BigDecimal?,
    @SerializedName("total_sales")
    val totalSales: Int?,
    @SerializedName("total_supply")
    val totalSupply: Int?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("num_owners")
    val numOwners: Int?,
    @SerializedName("average_price")
    val averagePrice: BigDecimal?,
    @SerializedName("num_reports")
    val numReports: Int?,
    @SerializedName("market_cap")
    val marketCap: BigDecimal?,
    @SerializedName("floor_price")
    val floorPrice: BigDecimal?
)
