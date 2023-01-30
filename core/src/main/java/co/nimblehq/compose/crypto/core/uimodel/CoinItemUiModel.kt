package co.nimblehq.compose.crypto.core.uimodel

import java.math.BigDecimal

data class CoinItemUiModel(
    val id: String,
    val symbol: String,
    val coinName: String,
    val image: String,
    val currentPrice: BigDecimal,
    val priceChangePercentage24hInCurrency: Double
)
