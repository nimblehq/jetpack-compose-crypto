package co.nimblehq.compose.crypto.ui.screens

import androidx.lifecycle.viewModelScope
import co.nimblehq.compose.crypto.domain.usecase.GetConnectionStatusUseCase
import co.nimblehq.compose.crypto.ui.base.*
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface Input : BaseInput

interface Output : BaseOutput {
    val isNetworkConnected: SharedFlow<Boolean?>
}

@HiltViewModel
class MainViewModel @Inject constructor(
    getConnectionStatusUseCase: GetConnectionStatusUseCase,
    dispatchersProvider: DispatchersProvider,
) : BaseViewModel(dispatchersProvider), Input, Output {
    private val _isNetworkConnected = MutableSharedFlow<Boolean?>()
    override val isNetworkConnected: SharedFlow<Boolean?>
        get() = _isNetworkConnected

    override val input: BaseInput = this
    override val output: BaseOutput = this

    init {
        getConnectionStatusUseCase()
            .catch {
                _error.emit(it)
            }
            .onEach {
                _isNetworkConnected.emit(it)
            }
            .flowOn(dispatchersProvider.io)
            .launchIn(viewModelScope)
    }
}
