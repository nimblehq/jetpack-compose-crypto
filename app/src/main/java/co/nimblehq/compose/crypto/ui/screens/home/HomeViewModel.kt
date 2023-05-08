package co.nimblehq.compose.crypto.ui.screens.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import co.nimblehq.compose.crypto.domain.usecase.GetMyCoinsUseCase
import co.nimblehq.compose.crypto.domain.usecase.GetTrendingCoinsPaginationUseCase
import co.nimblehq.compose.crypto.lib.IsLoading
import co.nimblehq.compose.crypto.ui.base.BaseInput
import co.nimblehq.compose.crypto.ui.base.BaseOutput
import co.nimblehq.compose.crypto.ui.base.BaseViewModel
import co.nimblehq.compose.crypto.ui.base.LoadingState
import co.nimblehq.compose.crypto.ui.navigation.AppDestination
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel
import co.nimblehq.compose.crypto.ui.uimodel.toUiModel
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

const val FIAT_CURRENCY = "usd"
private const val MY_COINS_ORDER = "market_cap_desc"
private const val MY_COINS_PRICE_CHANGE_IN_HOUR = "24h"
private const val MY_COINS_ITEM_PER_PAGE = 20
private const val MY_COINS_INITIAL_PAGE = 1

interface Input : BaseInput {

    fun loadData(isRefreshing: Boolean = false)

    fun getTrendingCoins()

    fun onMyCoinsItemClick(coin: CoinItemUiModel)

    fun onTrendingCoinsItemClick(coin: CoinItemUiModel)
}

interface Output : BaseOutput {

    val showMyCoinsLoading: StateFlow<IsLoading>

    val showTrendingCoinsLoading: StateFlow<LoadingState>

    val myCoins: StateFlow<List<CoinItemUiModel>>

    val trendingCoins: StateFlow<PagingData<CoinItemUiModel>>

    val myCoinsError: SharedFlow<Throwable?>

    val trendingCoinsError: SharedFlow<Throwable?>
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val getMyCoinsUseCase: GetMyCoinsUseCase,
    private val getTrendingCoinsPaginationUseCase: GetTrendingCoinsPaginationUseCase,
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

    private val _trendingCoins = MutableStateFlow<PagingData<CoinItemUiModel>>(PagingData.empty())
    override val trendingCoins: StateFlow<PagingData<CoinItemUiModel>>
        get() = _trendingCoins

    private val _myCoinsError = MutableStateFlow<Throwable?>(null)
    override val myCoinsError: StateFlow<Throwable?>
        get() = _myCoinsError

    private val _trendingCoinsError = MutableStateFlow<Throwable?>(null)
    override val trendingCoinsError: StateFlow<Throwable?>
        get() = _trendingCoinsError

    init {
        loadData()
    }

    override fun loadData(isRefreshing: Boolean) {
        getMyCoins(isRefreshing = isRefreshing)
        if (!isRefreshing) {
            // Invoke on initial load only. Refreshing will be handled by LazyPagingItems.refresh()
            getTrendingCoins()
        }
    }

    private fun getMyCoins(isRefreshing: Boolean) = execute {
        if (isRefreshing) {
            showLoading()
        } else {
            _showMyCoinsLoading.value = true
        }
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
                _myCoinsError.emit(e)
            }
            .collect { coins ->
                _myCoins.emit(coins.map { it.toUiModel() })
            }
        if (isRefreshing) hideLoading() else _showMyCoinsLoading.value = false
    }

    override fun getTrendingCoins() {
        execute {
            getTrendingCoinsPaginationUseCase.execute(
                GetTrendingCoinsPaginationUseCase.Input(
                    currency = FIAT_CURRENCY,
                    order = MY_COINS_ORDER,
                    priceChangeInHour = MY_COINS_PRICE_CHANGE_IN_HOUR,
                    itemPerPage = MY_COINS_ITEM_PER_PAGE
                )
            ).catch { e ->
                _trendingCoinsError.emit(e)
            }.cachedIn(
                viewModelScope
            ).collect { coins ->
                val newCoinList = coins.map { it.toUiModel() }
                _trendingCoins.emit(newCoinList)
            }
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
