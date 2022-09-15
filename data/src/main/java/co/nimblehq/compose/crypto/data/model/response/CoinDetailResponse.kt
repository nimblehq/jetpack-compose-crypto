package co.nimblehq.compose.crypto.data.model.response

import co.nimblehq.compose.crypto.data.extension.orZero
import co.nimblehq.compose.crypto.domain.model.CoinDetail
import com.squareup.moshi.Json
import java.math.BigDecimal

data class CoinDetailResponse(
    @Json(name = "id")
    val id: String?,
    @Json(name = "symbol")
    val symbol: String?,
    @Json(name = "name")
    val coinName: String?,
    @Json(name = "image")
    val image: ImageResponse?,
    @Json(name = "market_data")
    val marketData: MarketDataResponse?
) {

    data class ImageResponse(
        @Json(name = "thumb")
        val thumb: String?,
        @Json(name = "small")
        val small: String?,
        @Json(name = "large")
        val large: String?,
    )

    data class MarketDataResponse(
        @Json(name = "current_price")
        val currentPrice: Map<String, BigDecimal>?,
        @Json(name = "ath")
        val ath: Map<String, BigDecimal>?,
        @Json(name = "ath_change_percentage")
        val athChangePercentage: Map<String, Double>?,
        @Json(name = "ath_date")
        val athDate: Map<String, String>?,
        @Json(name = "atl")
        val atl: Map<String, BigDecimal>?,
        @Json(name = "atl_change_percentage")
        val atlChangePercentage: Map<String, Double>?,
        @Json(name = "atl_date")
        val atlDate: Map<String, String>?,
        @Json(name = "market_cap")
        val marketCap: Map<String, BigDecimal>?,
        @Json(name = "market_cap_rank")
        val marketCapRank: Int?,
        @Json(name = "fully_diluted_valuation")
        val fullyDilutedValuation: Map<String, BigDecimal>?,
        @Json(name = "total_volume")
        val totalVolume: Map<String, BigDecimal>?,
        @Json(name = "high_24h")
        val high24h: Map<String, BigDecimal>?,
        @Json(name = "low_24h")
        val low24h: Map<String, BigDecimal>?,

        @Json(name = "price_change_24h")
        val priceChange24h: BigDecimal?,
        @Json(name = "price_change_percentage_24h")
        val priceChangePercentage24h: Double?,
        @Json(name = "price_change_percentage_7d")
        val priceChangePercentage7d: Double?,
        @Json(name = "price_change_percentage_14d")
        val priceChangePercentage14d: Double?,
        @Json(name = "price_change_percentage_30d")
        val priceChangePercentage30d: Double?,
        @Json(name = "price_change_percentage_60d")
        val priceChangePercentage60d: Double?,
        @Json(name = "price_change_percentage_200d")
        val priceChangePercentage200d: Double?,
        @Json(name = "price_change_percentage_1y")
        val priceChangePercentage1y: Double?,
        @Json(name = "market_cap_change_24h")
        val marketCapChange24h: BigDecimal?,
        @Json(name = "market_cap_change_percentage_24h")
        val marketCapChangePercentage24h: Double?,

        @Json(name = "price_change_24h_in_currency")
        val priceChange24hInCurrency: Map<String, BigDecimal>?,
        @Json(name = "price_change_percentage_24h_in_currency")
        val priceChangePercentage24hInCurrency: Map<String, Double>?,
        @Json(name = "price_change_percentage_7d_in_currency")
        val priceChangePercentage7dInCurrency: Map<String, Double>?,
        @Json(name = "price_change_percentage_14d_in_currency")
        val priceChangePercentage14dInCurrency: Map<String, Double>?,
        @Json(name = "price_change_percentage_30d_in_currency")
        val priceChangePercentage30dInCurrency: Map<String, Double>?,
        @Json(name = "price_change_percentage_60d_in_currency")
        val priceChangePercentage60dInCurrency: Map<String, Double>?,
        @Json(name = "price_change_percentage_200d_in_currency")
        val priceChangePercentage200dInCurrency: Map<String, Double>?,
        @Json(name = "price_change_percentage_1y_in_currency")
        val priceChangePercentage1yInCurrency: Map<String, Double>?,
        @Json(name = "market_cap_change_24h_in_currency")
        val marketCapChange24hInCurrency: Map<String, BigDecimal>?,
        @Json(name = "market_cap_change_percentage_24h_in_currency")
        val marketCapChangePercentage24hInCurrency: Map<String, Double>?,

        @Json(name = "total_supply")
        val totalSupply: BigDecimal?,
        @Json(name = "max_supply")
        val maxSupply: BigDecimal?,
        @Json(name = "circulating_supply")
        val circulatingSupply: BigDecimal?,

        @Json(name = "last_updated")
        val lastUpdated: String?,
    )
}

internal fun CoinDetailResponse.toModel() = CoinDetail(
    id = id.orEmpty(),
    symbol = symbol.orEmpty(),
    coinName = coinName.orEmpty(),

    image = image?.toModel(),
    marketData = marketData?.toModel(),
)

private fun CoinDetailResponse.ImageResponse.toModel() = CoinDetail.Image(
    thumb = thumb.orEmpty(),
    small = small.orEmpty(),
    large = large.orEmpty(),
)

private fun CoinDetailResponse.MarketDataResponse.toModel() = CoinDetail.MarketData(
    currentPrice = currentPrice.orEmpty(),
    ath = ath.orEmpty(),
    athChangePercentage = athChangePercentage.orEmpty(),
    athDate = athDate.orEmpty(),
    atl = atl.orEmpty(),
    atlChangePercentage = atlChangePercentage.orEmpty(),
    atlDate = atlDate.orEmpty(),
    marketCap = marketCap.orEmpty(),
    marketCapRank = marketCapRank.orZero(),
    fullyDilutedValuation = fullyDilutedValuation.orEmpty(),
    totalVolume = totalVolume.orEmpty(),
    high24h = high24h.orEmpty(),
    low24h = low24h.orEmpty(),

    priceChange24h = priceChange24h.orZero(),
    priceChangePercentage24h = priceChangePercentage24h.orZero(),
    priceChangePercentage7d = priceChangePercentage7d.orZero(),
    priceChangePercentage14d = priceChangePercentage14d.orZero(),
    priceChangePercentage30d = priceChangePercentage30d.orZero(),
    priceChangePercentage60d = priceChangePercentage60d.orZero(),
    priceChangePercentage200d = priceChangePercentage200d.orZero(),
    priceChangePercentage1y = priceChangePercentage1y.orZero(),
    marketCapChange24h = marketCapChange24h.orZero(),
    marketCapChangePercentage24h = marketCapChangePercentage24h.orZero(),

    priceChange24hInCurrency = priceChange24hInCurrency.orEmpty(),
    priceChangePercentage24hInCurrency = priceChangePercentage24hInCurrency.orEmpty(),
    priceChangePercentage7dInCurrency = priceChangePercentage7dInCurrency.orEmpty(),
    priceChangePercentage14dInCurrency = priceChangePercentage14dInCurrency.orEmpty(),
    priceChangePercentage30dInCurrency = priceChangePercentage30dInCurrency.orEmpty(),
    priceChangePercentage60dInCurrency = priceChangePercentage60dInCurrency.orEmpty(),
    priceChangePercentage200dInCurrency = priceChangePercentage200dInCurrency.orEmpty(),
    priceChangePercentage1yInCurrency = priceChangePercentage1yInCurrency.orEmpty(),
    marketCapChange24hInCurrency = marketCapChange24hInCurrency.orEmpty(),
    marketCapChangePercentage24hInCurrency = marketCapChangePercentage24hInCurrency.orEmpty(),

    totalSupply = totalSupply.orZero(),
    maxSupply = maxSupply.orZero(),
    circulatingSupply = circulatingSupply.orZero(),

    lastUpdated = lastUpdated.orEmpty(),
)
