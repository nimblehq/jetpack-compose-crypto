package co.nimblehq.compose.crypto.domain.model

data class TrendingItem(
    val id: String,
    val symbol: String,
    val coinName: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Long,
    val marketCapRank: Int,
    val fullyDilutedValuation: Long,
    val totalVolume: Long,
    val high24h: Double,
    val low24h: Double,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val marketCapChange24h: Double,
    val marketCapChangePercentage24h: Double,
    val circulatingSupply: Double,
    val totalSupply: Double,
    val maxSupply: Long,
    val ath: Double,
    val athChangePercentage: Double,
    val athDate: String,
    val atl: Double,
    val atlChangePercentage: Double,
    val atlDate: String,
    val roi: RoiItem?,
    val lastUpdated: String,
    val priceChangePercentage24hInCurrency: Double
) {

    data class RoiItem(
        val times: Double,
        val currency: String,
        val percentage: Double
    )
}
