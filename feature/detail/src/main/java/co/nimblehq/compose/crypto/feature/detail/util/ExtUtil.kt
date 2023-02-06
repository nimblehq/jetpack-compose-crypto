package co.nimblehq.compose.crypto.feature.detail.util

import co.nimblehq.compose.crypto.core.common.FIAT_CURRENCY
import co.nimblehq.compose.crypto.core.uimodel.CoinDetailUiModel
import co.nimblehq.compose.crypto.data.extension.orZero
import co.nimblehq.compose.crypto.domain.detail.model.CoinDetail

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
