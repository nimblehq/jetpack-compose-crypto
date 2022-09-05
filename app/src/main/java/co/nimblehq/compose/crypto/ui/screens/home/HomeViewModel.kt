package co.nimblehq.compose.crypto.ui.screens.home

import co.nimblehq.compose.crypto.domain.model.CoinItem
import co.nimblehq.compose.crypto.domain.usecase.GetMyCoinsUseCase
import co.nimblehq.compose.crypto.ui.base.*
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface Output : BaseOutput {
    val myCoins: StateFlow<List<CoinItem>>
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val getMyCoinsUseCase: GetMyCoinsUseCase
) : BaseViewModel(dispatchers), BaseInput, Output {

    override val input = this
    override val output = this

    private val _myCoins = MutableStateFlow<List<CoinItem>>(emptyList())
    override val myCoins: StateFlow<List<CoinItem>>
        get() = _myCoins

    init {
        execute {
            showLoading()
            getMyCoinsUseCase.execute(
                GetMyCoinsUseCase.Input(
                    currency = "usd",
                    order = "market_cap_desc",
                    priceChangeInHour = "24h",
                    itemPerPage = 10,
                    page = 1
                )
            )
                .catch { e ->
                    _error.emit(e)
                }
                .collect {
                    _myCoins.emit(it)
                }
            hideLoading()
        }
    }
}
