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
    id = id.orEmpty(),
    symbol = symbol.orEmpty(),
    coinName = coinName.orEmpty(),
    currentPrice = currentPrice ?: 0,
    marketCap = marketCap ?: 0,
    marketCapRank = marketCapRank  ?: 0,
    totalVolume = totalVolume ?: 0,
    high24h = high24h ?: 0,
    low24h = low24h ?: 0,
    priceChange24h = priceChange24h ?: 0.0,
    priceChangePercentage24h = priceChangePercentage24h ?: 0.0,
    marketCapChange24h = marketCapChange24h ?: 0.0,
    marketCapChangePercentage24h = marketCapChangePercentage24h ?: 0.0,
    priceChangePercentage24hInCurrency = priceChangePercentage24hInCurrency ?: 0.0
)

fun List<CoinItemResponse>.toModels() = this.map { it.toModel() }
