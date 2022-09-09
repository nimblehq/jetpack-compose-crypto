package co.nimblehq.compose.crypto.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel
import java.math.BigDecimal

@Suppress("MagicNumber")
internal val coinItemPreview = CoinItemUiModel(
    id = "bitcoin",
    symbol = "btc",
    coinName = "Bitcoin",
    image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
    currentPrice = BigDecimal(21953),
    priceChangePercentage24hInCurrency = 3.672009841642702
)

class CoinItemPreviewParameterProvider : PreviewParameterProvider<CoinItemUiModel> {
    override val values = sequenceOf(
        coinItemPreview
    )
}
