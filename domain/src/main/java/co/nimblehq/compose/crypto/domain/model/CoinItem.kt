package co.nimblehq.compose.crypto.domain.model

data class CoinItem(
    val id: String?,
    val symbol: String?,
    val coinName: String?,
    val currentPrice: Int?,
    val marketCap: Long?,
    val marketCapRank: Int?,
    val totalVolume: Long?,
    val high24h: Long?,
    val low24h: Long?,
    val priceChange24h: Double?,
    val priceChangePercentage24h: Double?,
    val marketCapChange24h: Double?,
    val marketCapChangePercentage24h: Double?,
    val priceChangePercentage24hInCurrency: Double?
)
