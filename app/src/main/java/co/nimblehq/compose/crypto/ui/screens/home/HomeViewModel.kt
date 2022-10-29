package co.nimblehq.compose.crypto.ui.screens.home

import co.nimblehq.compose.crypto.domain.usecase.GetMyCoinsUseCase
import co.nimblehq.compose.crypto.domain.usecase.GetTrendingCoinsUseCase
import co.nimblehq.compose.crypto.lib.IsLoading
import co.nimblehq.compose.crypto.ui.base.BaseInput
import co.nimblehq.compose.crypto.ui.base.BaseOutput
import co.nimblehq.compose.crypto.ui.base.BaseViewModel
import co.nimblehq.compose.crypto.ui.navigation.AppDestination
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel
import co.nimblehq.compose.crypto.ui.uimodel.toUiModel
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

const val FIAT_CURRENCY = "usd"
private const val MY_COINS_ORDER = "market_cap_desc"
private const val MY_COINS_PRICE_CHANGE_IN_HOUR = "24h"
private const val MY_COINS_ITEM_PER_PAGE = 10
private const val MY_COINS_INITIAL_PAGE = 1

interface Input : BaseInput {

    fun onMyCoinsItemClick(coin: CoinItemUiModel)

    fun onTrendingCoinsItemClick(coin: CoinItemUiModel)
}

interface Output : BaseOutput {

    val myCoins: StateFlow<List<CoinItemUiModel>>

    val trendingCoins: StateFlow<List<CoinItemUiModel>>
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val getMyCoinsUseCase: GetMyCoinsUseCase,
    private val getTrendingCoinsUseCase: GetTrendingCoinsUseCase
) : BaseViewModel(dispatchers), Input, Output {

    override val input = this
    override val output = this

    private val _showMyCoinsLoading = MutableStateFlow(false)
    val showMyCoinsLoading: StateFlow<IsLoading>
        get() = _showMyCoinsLoading

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: SharedFlow<IsLoading>
        get() = _isRefreshing

    private val _showTrendingCoinsLoading = MutableStateFlow(false)
    val showTrendingCoinsLoading: StateFlow<IsLoading>
        get() = _showTrendingCoinsLoading

    private val _myCoins = MutableStateFlow<List<CoinItemUiModel>>(emptyList())
    override val myCoins: StateFlow<List<CoinItemUiModel>>
        get() = _myCoins

    private val _trendingCoins = MutableStateFlow<List<CoinItemUiModel>>(emptyList())
    override val trendingCoins: StateFlow<List<CoinItemUiModel>>
        get() = _trendingCoins

    init {
        getMyCoins()
        getTrendingCoins()
    }

    private fun getMyCoins() = execute {
        _showMyCoinsLoading.value = true
        getMyCoinsUseCase.execute(
            GetMyCoinsUseCase.Input(
                currency = FIAT_CURRENCY,
                order = MY_COINS_ORDER,
                priceChangeInHour = MY_COINS_PRICE_CHANGE_IN_HOUR,
                itemPerPage = MY_COINS_ITEM_PER_PAGE,
                page = MY_COINS_INITIAL_PAGE
            )
        )
            .catch { e ->
                _error.emit(e)
            }
            .collect { coins ->
                _myCoins.emit(coins.map { it.toUiModel() })
            }
        _showMyCoinsLoading.value = false
    }

    private fun getTrendingCoins() = execute {
        _showTrendingCoinsLoading.value = true
        getTrendingCoinsUseCase.execute(
            GetTrendingCoinsUseCase.Input(
                currency = FIAT_CURRENCY,
                order = MY_COINS_ORDER,
                priceChangeInHour = MY_COINS_PRICE_CHANGE_IN_HOUR,
                itemPerPage = MY_COINS_ITEM_PER_PAGE,
                page = MY_COINS_INITIAL_PAGE
            )
        )
            .catch { e ->
                _error.emit(e)
            }
            .collect { coins ->
                _trendingCoins.emit(coins.map { it.toUiModel() })
            }
        _showTrendingCoinsLoading.value = false
    }

    fun refresh() = execute {
        _isRefreshing.value = true

        delay(5000L)

        val getMyCoins = getMyCoinsUseCase.execute(
            GetMyCoinsUseCase.Input(
                currency = FIAT_CURRENCY,
                order = MY_COINS_ORDER,
                priceChangeInHour = MY_COINS_PRICE_CHANGE_IN_HOUR,
                itemPerPage = MY_COINS_ITEM_PER_PAGE,
                page = MY_COINS_INITIAL_PAGE
            )
        )

        val getTrendingCoins = getTrendingCoinsUseCase.execute(
            GetTrendingCoinsUseCase.Input(
                currency = FIAT_CURRENCY,
                order = MY_COINS_ORDER,
                priceChangeInHour = MY_COINS_PRICE_CHANGE_IN_HOUR,
                itemPerPage = MY_COINS_ITEM_PER_PAGE,
                page = MY_COINS_INITIAL_PAGE
            )
        )

        getMyCoins.zip(getTrendingCoins) { myCoins, trendingCoins ->
            _myCoins.emit(myCoins.map { it.toUiModel() })
            _trendingCoins.emit(trendingCoins.map { it.toUiModel() })
        }.catch { e ->
            _error.emit(e)
        }.collect()

        _isRefreshing.value = false
    }

    override fun onMyCoinsItemClick(coin: CoinItemUiModel) {
        execute {
            _navigator.emit(AppDestination.CoinDetail.buildDestination(coin.id))
        }
    }

    override fun onTrendingCoinsItemClick(coin: CoinItemUiModel) {
        execute {
            _navigator.emit(AppDestination.CoinDetail.buildDestination(coin.id))
        }
    }
}
