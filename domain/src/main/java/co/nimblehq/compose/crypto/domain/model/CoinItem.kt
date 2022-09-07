package co.nimblehq.compose.crypto.domain.model

import java.math.BigDecimal

data class CoinItem(
    val id: String,
    val symbol: String,
    val coinName: String,
    val image: String,
    val currentPrice: BigDecimal,
    val marketCap: BigDecimal,
    val marketCapRank: Int,
    val fullyDilutedValuation: BigDecimal,
    val totalVolume: BigDecimal,
    val high24h: BigDecimal,
    val low24h: BigDecimal,
    val priceChange24h: BigDecimal,
    val priceChangePercentage24h: Double,
    val marketCapChange24h: BigDecimal,
    val marketCapChangePercentage24h: Double,
    val circulatingSupply: BigDecimal,
    val totalSupply: BigDecimal,
    val maxSupply: BigDecimal,
    val ath: BigDecimal,
    val athChangePercentage: Double,
    val athDate: String,
    val atl: BigDecimal,
    val atlChangePercentage: Double,
    val atlDate: String,
    val roi: RoiItem?,
    val lastUpdated: String,
    val priceChangePercentage24hInCurrency: Double
) {

    data class RoiItem(
        val times: BigDecimal,
        val currency: String,
        val percentage: Double
    )
}
