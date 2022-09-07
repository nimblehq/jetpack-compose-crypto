package co.nimblehq.compose.crypto.data.model.response

import co.nimblehq.compose.crypto.domain.model.CoinItem
import com.squareup.moshi.Json
import java.math.BigDecimal

data class CoinItemResponse(
    val id: String?,
    val symbol: String?,
    @Json(name = "name")
    val coinName: String?,
    val image: String?,
    @Json(name = "current_price")
    val currentPrice: BigDecimal?,
    @Json(name = "market_cap")
    val marketCap: BigDecimal?,
    @Json(name = "market_cap_rank")
    val marketCapRank: Int?,
    @Json(name = "total_volume")
    val totalVolume: BigDecimal?,
    @Json(name = "high_24h")
    val high24h: BigDecimal?,
    @Json(name = "low_24h")
    val low24h: BigDecimal?,
    @Json(name = "price_change_24h")
    val priceChange24h: BigDecimal?,
    @Json(name = "price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
    @Json(name = "market_cap_change_24h")
    val marketCapChange24h: BigDecimal?,
    @Json(name = "market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double?,
    @Json(name = "price_change_percentage_24h_in_currency")
    val priceChangePercentage24hInCurrency: Double?
)

@Suppress("ComplexMethod")
private fun CoinItemResponse.toModel() = CoinItem(
    id = id.orEmpty(),
    symbol = symbol.orEmpty(),
    coinName = coinName.orEmpty(),
    image = image.orEmpty(),
    currentPrice = currentPrice ?: BigDecimal.ZERO,
    marketCap = marketCap ?: BigDecimal.ZERO,
    marketCapRank = marketCapRank ?: 0,
    totalVolume = totalVolume ?: BigDecimal.ZERO,
    high24h = high24h ?: BigDecimal.ZERO,
    low24h = low24h ?: BigDecimal.ZERO,
    priceChange24h = priceChange24h ?: BigDecimal.ZERO,
    priceChangePercentage24h = priceChangePercentage24h ?: 0.0,
    marketCapChange24h = marketCapChange24h ?: BigDecimal.ZERO,
    marketCapChangePercentage24h = marketCapChangePercentage24h ?: 0.0,
    priceChangePercentage24hInCurrency = priceChangePercentage24hInCurrency ?: 0.0
)

fun List<CoinItemResponse>.toModels() = this.map { it.toModel() }
