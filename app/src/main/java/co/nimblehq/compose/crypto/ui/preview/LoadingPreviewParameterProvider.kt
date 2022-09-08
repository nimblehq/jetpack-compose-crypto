package co.nimblehq.compose.crypto.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import co.nimblehq.compose.crypto.lib.IsLoading

class LoadingPreviewParameterProvider : PreviewParameterProvider<IsLoading> {
    override val values = sequenceOf(
        false,
        true
    )
}
