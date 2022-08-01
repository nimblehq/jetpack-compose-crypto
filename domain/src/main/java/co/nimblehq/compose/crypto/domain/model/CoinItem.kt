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
    val priceChange24h: Long?,
    val priceChangePercentage24h: Int?,
    val marketCapChange24h: Long?,
    val marketCapChangePercentage24h: Int?,
    val priceChangePercentage24hInCurrency: Long?
)
