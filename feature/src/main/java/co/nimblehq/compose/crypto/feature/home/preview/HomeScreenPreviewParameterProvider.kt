package co.nimblehq.compose.crypto.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import co.nimblehq.compose.crypto.core.LoadingState
import co.nimblehq.compose.crypto.core.lib.IsLoading
import co.nimblehq.compose.crypto.core.uimodel.CoinItemUiModel

class HomeScreenPreviewParameterProvider : PreviewParameterProvider<HomeScreenParams> {
    override val values = sequenceOf(
        HomeScreenParams(
            listOf(coinItemPreview),
            listOf(coinItemPreview),
            false,
            LoadingState.Idle
        ),
        HomeScreenParams(
            listOf(coinItemPreview),
            listOf(coinItemPreview),
            true,
            LoadingState.Loading
        ),
    )
}

data class HomeScreenParams(
    val myCoins: List<CoinItemUiModel>,
    val trendingCoins: List<CoinItemUiModel>,
    val isMyCoinsLoading: IsLoading,
    val isTrendingCoinsLoading: LoadingState
)
