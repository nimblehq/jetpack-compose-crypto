package co.nimblehq.compose.crypto.domain.model

import java.math.BigDecimal

data class CoinItem(
    val id: String,
    val symbol: String,
    val coinName: String,
    val currentPrice: BigDecimal,
    val marketCap: BigDecimal,
    val marketCapRank: Int,
    val totalVolume: BigDecimal,
    val high24h: BigDecimal,
    val low24h: BigDecimal,
    val priceChange24h: BigDecimal,
    val priceChangePercentage24h: Double,
    val marketCapChange24h: BigDecimal,
    val marketCapChangePercentage24h: Double,
    val priceChangePercentage24hInCurrency: Double
)
