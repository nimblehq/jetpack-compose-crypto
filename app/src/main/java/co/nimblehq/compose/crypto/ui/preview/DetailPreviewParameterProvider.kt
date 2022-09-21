package co.nimblehq.compose.crypto.ui.preview

import co.nimblehq.compose.crypto.ui.uimodel.CoinDetailUiModel
import java.math.BigDecimal

@Suppress("MagicNumber")
internal val detailPreview = CoinDetailUiModel(
    coinName = "Bitcoin",
    image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
    currentPrice = BigDecimal(21953),
    priceChangePercentage24hInCurrency = 3.672009841642702,
    marketCap = BigDecimal(175693456.56),
    marketCapChangePercentage24h = 7.5567,
    ath = BigDecimal(1756934),
    athChangePercentage = 6.8900,
    atl = BigDecimal(0.745846945),
    atlChangePercentage = 8.832344
)
