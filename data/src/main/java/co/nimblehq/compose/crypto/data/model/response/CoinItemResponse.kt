package co.nimblehq.compose.crypto.data.model.response

import co.nimblehq.compose.crypto.domain.model.CoinItem
import com.squareup.moshi.Json

data class CoinItemResponse(
    @Json(name = "id")
    val id: String?,
    @Json(name = "symbol")
    val symbol: String?,
    @Json(name = "name")
    val coinName: String?,
    @Json(name = "current_price")
    val currentPrice: Int?,
    @Json(name = "market_cap")
    val marketCap: Long?,
    @Json(name = "market_cap_rank")
    val marketCapRank: Int?,
    @Json(name = "total_volume")
    val totalVolume: Long?,
    @Json(name = "high_24h")
    val high24h: Long?,
    @Json(name = "low_24h")
    val low24h: Long?,
    @Json(name = "price_change_24h")
    val priceChange24h: Double?,
    @Json(name = "price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
    @Json(name = "market_cap_change_24h")
    val marketCapChange24h: Double?,
    @Json(name = "market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double?,
    @Json(name = "price_change_percentage_24h_in_currency")
    val priceChangePercentage24hInCurrency: Double?
)

private fun CoinItemResponse.toModel() = CoinItem(
    id = id,
    symbol = symbol,
    coinName = coinName,
    currentPrice = currentPrice,
    marketCap = marketCap,
    marketCapRank = marketCapRank,
    totalVolume = totalVolume,
    high24h = high24h,
    low24h = low24h,
    priceChange24h = priceChange24h,
    priceChangePercentage24h = priceChangePercentage24h,
    marketCapChange24h = marketCapChange24h,
    marketCapChangePercentage24h = marketCapChangePercentage24h,
    priceChangePercentage24hInCurrency = priceChangePercentage24hInCurrency
)

fun List<CoinItemResponse>.toModels() = this.map { it.toModel() }
