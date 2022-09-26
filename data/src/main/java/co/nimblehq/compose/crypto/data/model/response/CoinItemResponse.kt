package co.nimblehq.compose.crypto.data.model.response

import co.nimblehq.compose.crypto.data.extension.orZero
import co.nimblehq.compose.crypto.domain.model.CoinItem
import com.squareup.moshi.Json
import java.math.BigDecimal

data class CoinItemResponse(
    @Json(name = "id")
    val id: String?,
    @Json(name = "symbol")
    val symbol: String?,
    @Json(name = "name")
    val coinName: String?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "current_price")
    val currentPrice: BigDecimal?,
    @Json(name = "market_cap")
    val marketCap: BigDecimal?,
    @Json(name = "market_cap_rank")
    val marketCapRank: Int?,
    @Json(name = "fully_diluted_valuation")
    val fullyDilutedValuation: BigDecimal?,
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
    @Json(name = "circulating_supply")
    val circulatingSupply: BigDecimal?,
    @Json(name = "total_supply")
    val totalSupply: BigDecimal?,
    @Json(name = "max_supply")
    val maxSupply: BigDecimal?,
    @Json(name = "ath")
    val ath: BigDecimal?,
    @Json(name = "ath_change_percentage")
    val athChangePercentage: Double?,
    @Json(name = "ath_date")
    val athDate: String?,
    @Json(name = "atl")
    val atl: BigDecimal?,
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
        val times: BigDecimal?,
        @Json(name = "currency")
        val currency: String?,
        @Json(name = "percentage")
        val percentage: Double?
    )
}

internal fun CoinItemResponse.toModel() = CoinItem(
    id = id.orEmpty(),
    symbol = symbol.orEmpty(),
    coinName = coinName.orEmpty(),
    image = image.orEmpty(),
    currentPrice = currentPrice.orZero(),
    marketCap = marketCap.orZero(),
    marketCapRank = marketCapRank.orZero(),
    fullyDilutedValuation = fullyDilutedValuation.orZero(),
    totalVolume = totalVolume.orZero(),
    high24h = high24h.orZero(),
    low24h = low24h.orZero(),
    priceChange24h = priceChange24h.orZero(),
    priceChangePercentage24h = priceChangePercentage24h.orZero(),
    marketCapChange24h = marketCapChange24h.orZero(),
    marketCapChangePercentage24h = marketCapChangePercentage24h.orZero(),
    circulatingSupply = circulatingSupply.orZero(),
    totalSupply = totalSupply.orZero(),
    maxSupply = maxSupply.orZero(),
    ath = ath.orZero(),
    athChangePercentage = athChangePercentage.orZero(),
    athDate = athDate.orEmpty(),
    atl = atl.orZero(),
    atlChangePercentage = atlChangePercentage.orZero(),
    atlDate = atlDate.orEmpty(),
    roi = roi?.toModel(),
    lastUpdated = lastUpdated.orEmpty(),
    priceChangePercentage24hInCurrency = priceChangePercentage24hInCurrency.orZero()
)

private fun CoinItemResponse.RoiItemResponse.toModel() = CoinItem.RoiItem(
    times = times.orZero(),
    currency = currency.orEmpty(),
    percentage = percentage.orZero()
)
