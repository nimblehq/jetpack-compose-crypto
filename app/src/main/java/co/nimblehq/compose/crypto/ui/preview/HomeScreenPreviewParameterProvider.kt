package co.nimblehq.compose.crypto.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import co.nimblehq.compose.crypto.lib.IsLoading
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel

class HomeScreenPreviewParameterProvider : PreviewParameterProvider<HomeScreenParams> {
    override val values = sequenceOf(
        HomeScreenParams(
            listOf(coinItemPreview),
            listOf(coinItemPreview),
            false
        ),
        HomeScreenParams(
            listOf(coinItemPreview),
            listOf(coinItemPreview),
            true
        ),
    )
}

data class HomeScreenParams(
    val myCoins: List<CoinItemUiModel>,
    val trendingCoins: List<CoinItemUiModel>,
    val isLoading: IsLoading
)
