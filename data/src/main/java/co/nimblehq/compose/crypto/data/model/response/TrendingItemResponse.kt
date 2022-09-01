package co.nimblehq.compose.crypto.data.model.response

import co.nimblehq.compose.crypto.domain.model.TrendingItem
import com.squareup.moshi.Json

data class TrendingItemResponse(
    @Json(name = "id")
    val id: String?,
    @Json(name = "symbol")
    val symbol: String?,
    @Json(name = "name")
    val coinName: String?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "current_price")
    val currentPrice: Double?,
    @Json(name = "market_cap")
    val marketCap: Long?,
    @Json(name = "market_cap_rank")
    val marketCapRank: Int?,
    @Json(name = "fully_diluted_valuation")
    val fullyDilutedValuation: Long?,
    @Json(name = "total_volume")
    val totalVolume: Long?,
    @Json(name = "high_24h")
    val high24h: Double?,
    @Json(name = "low_24h")
    val low24h: Double?,
    @Json(name = "price_change_24h")
    val priceChange24h: Double?,
    @Json(name = "price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
    @Json(name = "market_cap_change_24h")
    val marketCapChange24h: Double?,
    @Json(name = "market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double?,
    @Json(name = "circulating_supply")
    val circulatingSupply: Double?,
    @Json(name = "total_supply")
    val totalSupply: Double?,
    @Json(name = "max_supply")
    val maxSupply: Long?,
    @Json(name = "ath")
    val ath: Double?,
    @Json(name = "ath_change_percentage")
    val athChangePercentage: Double?,
    @Json(name = "ath_date")
    val athDate: String?,
    @Json(name = "atl")
    val atl: Double?,
    @Json(name = "atl_change_percentage")
    val atlChangePercentage: Double?,
    @Json(name = "atl_date")
    val atlDate: String?,
    @Json(name = "roi")
    val roi: RoiItemResponse?,
    @Json(name = "last_updated")
    val lastUpdated: String?,
    @Json(name = "price_change_percentage_24h_in_currency")
    val priceChangePercentage24hInCurrency: Double?
) {

    data class RoiItemResponse(
        @Json(name = "times")
        val times: Double?,
        @Json(name = "currency")
        val currency: String?,
        @Json(name = "percentage")
        val percentage: Double?
    )
}

@Suppress("ComplexMethod")
private fun TrendingItemResponse.toModel() = TrendingItem(
    id = id.orEmpty(),
    symbol = symbol.orEmpty(),
    coinName = coinName.orEmpty(),
    image = image.orEmpty(),
    currentPrice = currentPrice ?: 0.0,
    marketCap = marketCap ?: 0,
    marketCapRank = marketCapRank ?: 0,
    fullyDilutedValuation = fullyDilutedValuation ?: 0,
    totalVolume = totalVolume ?: 0,
    high24h = high24h ?: 0.0,
    low24h = low24h ?: 0.0,
    priceChange24h = priceChange24h ?: 0.0,
    priceChangePercentage24h = priceChangePercentage24h ?: 0.0,
    marketCapChange24h = marketCapChange24h ?: 0.0,
    marketCapChangePercentage24h = marketCapChangePercentage24h ?: 0.0,
    circulatingSupply = circulatingSupply ?: 0.0,
    totalSupply = totalSupply ?: 0.0,
    maxSupply = maxSupply ?: 0,
    ath = ath ?: 0.0,
    athChangePercentage = athChangePercentage ?: 0.0,
    athDate = athDate.orEmpty(),
    atl = atl ?: 0.0,
    atlChangePercentage = atlChangePercentage ?: 0.0,
    atlDate = atlDate.orEmpty(),
    roi = roi?.toModel(),
    lastUpdated = lastUpdated.orEmpty(),
    priceChangePercentage24hInCurrency = priceChangePercentage24hInCurrency ?: 0.0
)

fun List<TrendingItemResponse>.toModels() = this.map { it.toModel() }

private fun TrendingItemResponse.RoiItemResponse.toModel() = TrendingItem.RoiItem(
    times = times ?: 0.0,
    currency = currency.orEmpty(),
    percentage = percentage ?: 0.0
)
