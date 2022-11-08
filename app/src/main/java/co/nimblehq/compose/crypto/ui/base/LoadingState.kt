package co.nimblehq.compose.crypto.ui.base

sealed class LoadingState {
    object Idle : LoadingState()
    object Loading : LoadingState()
    object LoadingMore : LoadingState()
}
