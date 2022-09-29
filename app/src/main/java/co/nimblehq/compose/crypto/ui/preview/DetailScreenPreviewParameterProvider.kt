package co.nimblehq.compose.crypto.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import co.nimblehq.compose.crypto.ui.uimodel.CoinDetailUiModel

class DetailScreenPreviewParameterProvider : PreviewParameterProvider<DetailScreenParams> {
    override val values = sequenceOf(
        DetailScreenParams(
            detail = detailPreview,
            showLoading = false
        ),
        DetailScreenParams(
            detail = null,
            showLoading = true
        )
    )
}

data class DetailScreenParams(
    val detail: CoinDetailUiModel?,
    val showLoading: Boolean
)
