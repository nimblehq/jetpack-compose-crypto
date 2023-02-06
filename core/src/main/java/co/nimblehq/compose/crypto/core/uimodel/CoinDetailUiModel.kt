package co.nimblehq.compose.crypto.core.uimodel

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
