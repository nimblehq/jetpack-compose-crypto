package co.nimblehq.compose.crypto.domain.model

import java.math.BigDecimal

data class CoinDetail(
    val id: String,
    val symbol: String,
    val coinName: String,
    val image: Image?,
    val marketData: MarketData?
) {

    data class Image(
        val thumb: String,
        val small: String,
        val large: String,
    )

    data class MarketData(
        val currentPrice: Map<String, BigDecimal>,
        val ath: Map<String, BigDecimal>,
        val athChangePercentage: Map<String, Double>,
        val athDate: Map<String, String>,
        val atl: Map<String, BigDecimal>,
        val atlChangePercentage: Map<String, Double>,
        val atlDate: Map<String, String>,
        val marketCap: Map<String, BigDecimal>,
        val marketCapRank: Int,
        val fullyDilutedValuation: Map<String, BigDecimal>,
        val totalVolume: Map<String, BigDecimal>,
        val high24h: Map<String, BigDecimal>,
        val low24h: Map<String, BigDecimal>,

        val priceChange24h: BigDecimal,
        val priceChangePercentage24h: Double,
        val priceChangePercentage7d: Double,
        val priceChangePercentage14d: Double,
        val priceChangePercentage30d: Double,
        val priceChangePercentage60d: Double,
        val priceChangePercentage200d: Double,
        val priceChangePercentage1y: Double,
        val marketCapChange24h: BigDecimal,
        val marketCapChangePercentage24h: Double,

        val priceChange24hInCurrency: Map<String, BigDecimal>,
        val priceChangePercentage24hInCurrency: Map<String, Double>,
        val priceChangePercentage7dInCurrency: Map<String, Double>,
        val priceChangePercentage14dInCurrency: Map<String, Double>,
        val priceChangePercentage30dInCurrency: Map<String, Double>,
        val priceChangePercentage60dInCurrency: Map<String, Double>,
        val priceChangePercentage200dInCurrency: Map<String, Double>,
        val priceChangePercentage1yInCurrency: Map<String, Double>,
        val marketCapChange24hInCurrency: Map<String, BigDecimal>,
        val marketCapChangePercentage24hInCurrency: Map<String, Double>,

        val totalSupply: BigDecimal,
        val maxSupply: BigDecimal,
        val circulatingSupply: BigDecimal,

        val lastUpdated: String,
    )
}
