package co.nimblehq.compose.crypto.ui.screens.home

import co.nimblehq.compose.crypto.domain.usecase.GetMyCoinsUseCase
import co.nimblehq.compose.crypto.domain.usecase.GetTrendingCoinsUseCase
import co.nimblehq.compose.crypto.lib.IsLoading
import co.nimblehq.compose.crypto.ui.base.*
import co.nimblehq.compose.crypto.ui.navigation.AppDestination
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel
import co.nimblehq.compose.crypto.ui.uimodel.toUiModel
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

const val FIAT_CURRENCY = "usd"
private const val MY_COINS_ORDER = "market_cap_desc"
private const val MY_COINS_PRICE_CHANGE_IN_HOUR = "24h"
private const val MY_COINS_ITEM_PER_PAGE = 10
private const val MY_COINS_INITIAL_PAGE = 1

interface Input : BaseInput {

    fun loadData(isRefreshing: Boolean = false)

    fun getTrendingCoins(isRefreshing: Boolean = false, loadMore: Boolean = false)

    fun onMyCoinsItemClick(coin: CoinItemUiModel)

    fun onTrendingCoinsItemClick(coin: CoinItemUiModel)
}

interface Output : BaseOutput {

    val showMyCoinsLoading: StateFlow<IsLoading>

    val showTrendingCoinsLoading: StateFlow<LoadingState>

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
    override val showMyCoinsLoading: StateFlow<IsLoading>
        get() = _showMyCoinsLoading

    private val _showTrendingCoinsLoading = MutableStateFlow<LoadingState>(LoadingState.Idle)
    override val showTrendingCoinsLoading: StateFlow<LoadingState>
        get() = _showTrendingCoinsLoading

    private val _myCoins = MutableStateFlow<List<CoinItemUiModel>>(emptyList())
    override val myCoins: StateFlow<List<CoinItemUiModel>>
        get() = _myCoins

    private val _trendingCoins = MutableStateFlow<List<CoinItemUiModel>>(emptyList())
    override val trendingCoins: StateFlow<List<CoinItemUiModel>>
        get() = _trendingCoins

    private var page = MY_COINS_INITIAL_PAGE

    init {
        loadData()
    }

    override fun loadData(isRefreshing: Boolean) {
        getMyCoins(isRefreshing = isRefreshing)
        getTrendingCoins(isRefreshing = isRefreshing)
    }

    private fun getMyCoins(isRefreshing: Boolean) = execute {
        if (isRefreshing) showLoading() else _showMyCoinsLoading.value = true
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
        if (isRefreshing) hideLoading() else _showMyCoinsLoading.value = false
    }

    override fun getTrendingCoins(isRefreshing: Boolean, loadMore: Boolean) {
        if (_showTrendingCoinsLoading.value != LoadingState.Idle) return
        execute {
            if (isRefreshing) showLoading() else
                _showTrendingCoinsLoading.value = if (loadMore) LoadingState.LoadingMore else LoadingState.Loading
            getTrendingCoinsUseCase.execute(
                GetTrendingCoinsUseCase.Input(
                    currency = FIAT_CURRENCY,
                    order = MY_COINS_ORDER,
                    priceChangeInHour = MY_COINS_PRICE_CHANGE_IN_HOUR,
                    itemPerPage = MY_COINS_ITEM_PER_PAGE,
                    page = page
                )
            )
                .catch { e ->
                    _error.emit(e)
                }
                .collect { coins ->
                    val newCoinList = coins.map { it.toUiModel() }
                    _trendingCoins.emit(_trendingCoins.value + newCoinList)
                    page++
                }
            if (isRefreshing) hideLoading() else
                _showTrendingCoinsLoading.value = LoadingState.Idle
        }
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
