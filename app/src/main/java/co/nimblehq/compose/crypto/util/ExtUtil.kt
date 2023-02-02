package co.nimblehq.compose.crypto.util

import co.nimblehq.compose.crypto.core.uimodel.CoinItemUiModel
import co.nimblehq.compose.crypto.domain.model.CoinItem

fun CoinItem.toUiModel() = CoinItemUiModel(
    id = id,
    symbol = symbol,
    coinName = coinName,
    image = image,
    currentPrice = currentPrice,
    priceChangePercentage24hInCurrency = priceChangePercentage24hInCurrency
)
