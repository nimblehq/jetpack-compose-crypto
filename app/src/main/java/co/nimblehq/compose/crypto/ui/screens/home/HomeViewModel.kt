package co.nimblehq.compose.crypto.ui.screens.home

import co.nimblehq.compose.crypto.domain.usecase.GetMyCoinsUseCase
import co.nimblehq.compose.crypto.ui.base.*
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel
import co.nimblehq.compose.crypto.ui.uimodel.toUiModel
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val MY_COINS_CURRENCY = "usd"
private const val MY_COINS_ORDER = "market_cap_desc"
private const val MY_COINS_PRICE_CHANGE_IN_HOUR = "24h"
private const val MY_COINS_ITEM_PER_PAGE = 10

interface Output : BaseOutput {
    val myCoins: StateFlow<List<CoinItemUiModel>>
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val getMyCoinsUseCase: GetMyCoinsUseCase
) : BaseViewModel(dispatchers), BaseInput, Output {

    override val input = this
    override val output = this

    private val _myCoins = MutableStateFlow<List<CoinItemUiModel>>(emptyList())
    override val myCoins: StateFlow<List<CoinItemUiModel>>
        get() = _myCoins

    init {
        execute {
            showLoading()
            getMyCoinsUseCase.execute(
                GetMyCoinsUseCase.Input(
                    currency = MY_COINS_CURRENCY,
                    order = MY_COINS_ORDER,
                    priceChangeInHour = MY_COINS_PRICE_CHANGE_IN_HOUR,
                    itemPerPage = MY_COINS_ITEM_PER_PAGE,
                    page = 1
                )
            )
                .catch { e ->
                    _error.emit(e)
                }
                .collect { myCoins ->
                    _myCoins.emit(myCoins.map { it.toUiModel() })
                }
            hideLoading()
        }
    }
}
