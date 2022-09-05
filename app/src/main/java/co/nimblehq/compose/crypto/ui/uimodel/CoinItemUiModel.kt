package co.nimblehq.compose.crypto.ui.uimodel

import co.nimblehq.compose.crypto.domain.model.CoinItem
import java.math.BigDecimal

data class CoinItemUiModel(
    val id: String,
    val symbol: String,
    val coinName: String,
    val image: String,
    val currentPrice: BigDecimal,
    val priceChangePercentage24hInCurrency: Double
)

fun CoinItem.toUiModel() = CoinItemUiModel(
    id = id,
    symbol = symbol,
    coinName = coinName,
    image = image,
    currentPrice = currentPrice,
    priceChangePercentage24hInCurrency = priceChangePercentage24hInCurrency
)
