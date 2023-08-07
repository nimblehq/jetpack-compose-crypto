package co.nimblehq.compose.crypto

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import co.nimblehq.compose.crypto.domain.usecase.IsNetworkConnectedUseCase
import co.nimblehq.compose.crypto.util.DispatchersProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

@Composable
fun rememberCryptoAppState(
    isNetworkConnectedUseCase: IsNetworkConnectedUseCase,
    dispatchersProvider: DispatchersProvider,
) = remember(isNetworkConnectedUseCase, dispatchersProvider) {
    CryptoAppState(isNetworkConnectedUseCase, dispatchersProvider)
}

class CryptoAppState(
    isNetworkConnectedUseCase: IsNetworkConnectedUseCase,
    dispatchersProvider: DispatchersProvider,
) {
    private val _isNetworkConnected = MutableSharedFlow<Boolean?>()
    val isNetworkConnected: SharedFlow<Boolean?>
        get() = _isNetworkConnected

    private val _isNetworkError = MutableSharedFlow<Throwable?>()
    val isNetworkError: SharedFlow<Throwable?>
        get() = _isNetworkError

    init {
        isNetworkConnectedUseCase()
            .catch {
                _isNetworkError.emit(it)
            }
            .onEach {
                _isNetworkConnected.emit(it)
            }
            .flowOn(dispatchersProvider.io)
            .launchIn(CoroutineScope(dispatchersProvider.io))
    }
}
