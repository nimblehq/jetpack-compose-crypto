package co.nimblehq.compose.crypto.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.compose.crypto.model.UserUiModel
import co.nimblehq.compose.crypto.model.toUserUiModels
import co.nimblehq.compose.crypto.ui.base.BaseViewModel
import co.nimblehq.compose.crypto.ui.base.NavigationEvent
import co.nimblehq.compose.crypto.ui.screens.second.SecondBundle
import co.nimblehq.compose.crypto.domain.usecase.GetUsersUseCase
import co.nimblehq.compose.crypto.domain.usecase.UseCaseResult
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Output {

    val userUiModels: StateFlow<List<UserUiModel>>

    fun navigateToSecond(bundle: SecondBundle)

    fun navigateToCompose()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers), Output {

    private val _userUiModels = MutableStateFlow<List<UserUiModel>>(emptyList())
    override val userUiModels: StateFlow<List<UserUiModel>>
        get() = _userUiModels

    init {
        fetchUsers()
    }

    override fun navigateToSecond(bundle: SecondBundle) {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.Second(bundle))
        }
    }

    override fun navigateToCompose() {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.Compose)
        }
    }

    private fun fetchUsers() {
        showLoading()
        execute {
            when (val result = getUsersUseCase.execute()) {
                is UseCaseResult.Success -> _userUiModels.value = result.data.toUserUiModels()
                is UseCaseResult.Error -> _error.emit(result.exception.message.orEmpty())
            }
            hideLoading()
        }
    }
}
