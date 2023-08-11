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

    private val _networkError = MutableSharedFlow<Throwable?>()
    val networkError: SharedFlow<Throwable?>
        get() = _networkError

    init {
        isNetworkConnectedUseCase()
            .catch {
                _networkError.emit(it)
            }
            .onEach {
                _isNetworkConnected.emit(it)
            }
            .flowOn(dispatchersProvider.io)
            .launchIn(CoroutineScope(dispatchersProvider.io))
    }
}
