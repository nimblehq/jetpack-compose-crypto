package co.nimblehq.compose.crypto.ui.screens.detail

import co.nimblehq.compose.crypto.domain.usecase.GetCoinDetailUseCase
import co.nimblehq.compose.crypto.ui.base.*
import co.nimblehq.compose.crypto.ui.uimodel.CoinDetailUiModel
import co.nimblehq.compose.crypto.ui.uimodel.toUiModel
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface Input : BaseInput {

    fun getCoinId(coinId: String)
}

interface Output : BaseOutput {

    val coinDetailUiModel: StateFlow<CoinDetailUiModel?>
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val getCoinDetailUseCase: GetCoinDetailUseCase
) : BaseViewModel(dispatchers), Input, Output {

    override val input = this
    override val output = this

    private val _coinDetailUiModel = MutableStateFlow<CoinDetailUiModel?>(null)
    override val coinDetailUiModel: StateFlow<CoinDetailUiModel?>
        get() = _coinDetailUiModel

    override fun getCoinId(coinId: String) {
        getCoinDetail(coinId = coinId)
    }

    private fun getCoinDetail(coinId: String) = execute {
        showLoading()
        getCoinDetailUseCase.execute(coinId = coinId)
            .catch { e ->
                _error.emit(e)
            }
            .collect { coinDetail ->
                _coinDetailUiModel.emit(coinDetail.toUiModel())
            }
        hideLoading()
    }
}
