package co.nimblehq.compose.crypto.core

sealed class LoadingState {
    object Idle : LoadingState()
    object Loading : LoadingState()
    object LoadingMore : LoadingState()
}
