package co.nimblehq.compose.crypto.ui.uimodel

import co.nimblehq.compose.crypto.data.extension.orZero
import co.nimblehq.compose.crypto.domain.model.CoinDetail
import java.math.BigDecimal

private const val COIN_CURRENCY_KEY = "usd"

data class CoinDetailUiModel(
    val coinName: String,
    val image: String,
    val currentPrice: BigDecimal,
    val priceChangePercentage24hInCurrency: Double,
    val marketCap: BigDecimal,
    val marketCapChangePercentage24h: Double,
    val ath: BigDecimal,
    val athChangePercentage: Double,
    val atl: BigDecimal,
    val atlChangePercentage: Double
)

fun CoinDetail.toUiModel() = CoinDetailUiModel(
    coinName = coinName,
    image = image?.small.orEmpty(),
    currentPrice = marketData?.currentPrice?.getValue(COIN_CURRENCY_KEY).orZero(),
    priceChangePercentage24hInCurrency = marketData?.priceChangePercentage24hInCurrency
        ?.getValue(COIN_CURRENCY_KEY).orZero(),
    marketCap = marketData?.marketCap?.getValue(COIN_CURRENCY_KEY).orZero(),
    marketCapChangePercentage24h = marketData?.marketCapChangePercentage24h.orZero(),
    ath = marketData?.ath?.getValue(COIN_CURRENCY_KEY).orZero(),
    athChangePercentage = marketData?.athChangePercentage?.getValue(COIN_CURRENCY_KEY).orZero(),
    atl = marketData?.atl?.getValue(COIN_CURRENCY_KEY).orZero(),
    atlChangePercentage = marketData?.atlChangePercentage?.getValue(COIN_CURRENCY_KEY).orZero()
)
