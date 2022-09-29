package co.nimblehq.compose.crypto.ui.uimodel

import co.nimblehq.compose.crypto.data.extension.orZero
import co.nimblehq.compose.crypto.domain.model.CoinDetail
import co.nimblehq.compose.crypto.ui.screens.home.FIAT_CURRENCY
import java.math.BigDecimal

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
    currentPrice = marketData?.currentPrice?.getValue(FIAT_CURRENCY).orZero(),
    priceChangePercentage24hInCurrency = marketData?.priceChangePercentage24hInCurrency
        ?.getValue(FIAT_CURRENCY).orZero(),
    marketCap = marketData?.marketCap?.getValue(FIAT_CURRENCY).orZero(),
    marketCapChangePercentage24h = marketData?.marketCapChangePercentage24h.orZero(),
    ath = marketData?.ath?.getValue(FIAT_CURRENCY).orZero(),
    athChangePercentage = marketData?.athChangePercentage?.getValue(FIAT_CURRENCY).orZero(),
    atl = marketData?.atl?.getValue(FIAT_CURRENCY).orZero(),
    atlChangePercentage = marketData?.atlChangePercentage?.getValue(FIAT_CURRENCY).orZero()
)
