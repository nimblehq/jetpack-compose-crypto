package co.nimblehq.compose.crypto.ui.screens.detail

import co.nimblehq.compose.crypto.domain.model.CoinPrice
import co.nimblehq.compose.crypto.domain.usecase.GetCoinDetailUseCase
import co.nimblehq.compose.crypto.domain.usecase.GetCoinPricesUseCase
import co.nimblehq.compose.crypto.ui.base.BaseInput
import co.nimblehq.compose.crypto.ui.base.BaseOutput
import co.nimblehq.compose.crypto.ui.base.BaseViewModel
import co.nimblehq.compose.crypto.ui.components.chartintervals.TimeIntervals
import co.nimblehq.compose.crypto.ui.uimodel.CoinDetailUiModel
import co.nimblehq.compose.crypto.ui.uimodel.toUiModel
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNot
import java.util.*
import javax.inject.Inject

private const val COIN_PRICES_CURRENCY = "usd"

interface Input : BaseInput {

    fun getCoinId(coinId: String)
}

interface Output : BaseOutput {

    val coinDetailUiModel: StateFlow<CoinDetailUiModel?>
    val coinPrices: StateFlow<List<CoinPrice>>
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    private val getCoinPricesUseCase: GetCoinPricesUseCase
) : BaseViewModel(dispatchers), Input, Output {

    override val input = this
    override val output = this

    private val _coinDetailUiModel = MutableStateFlow<CoinDetailUiModel?>(null)
    override val coinDetailUiModel: StateFlow<CoinDetailUiModel?>
        get() = _coinDetailUiModel

    private val _coinPrices = MutableStateFlow<List<CoinPrice>>(emptyList())
    override val coinPrices: StateFlow<List<CoinPrice>>
        get() = _coinPrices

    override fun getCoinId(coinId: String) {
        getCoinDetail(coinId = coinId)
        getCoinPrices(coinId = coinId)
    }

    private fun getCoinDetail(coinId: String) = execute {
        showLoading()
        getCoinDetailUseCase.execute(coinId = coinId)
            .catch { e ->
                hideLoading()
                _error.emit(e)
            }
            .collect { coinDetail ->
                hideLoading()
                _coinDetailUiModel.emit(coinDetail.toUiModel())
            }
        hideLoading()
    }

    fun getCoinPrices(coinId: String, timeIntervals: TimeIntervals = TimeIntervals.ONE_DAY) =
        execute {
            val calendar = Calendar.getInstance()
            val fromTimestamp = when (timeIntervals) {
                TimeIntervals.ONE_DAY -> calendar.apply {
                    add(Calendar.DAY_OF_YEAR, -1)
                }
                TimeIntervals.ONE_WEEK -> calendar.apply {
                    add(Calendar.DAY_OF_YEAR, -7)
                }
                TimeIntervals.ONE_MONTH -> calendar.apply {
                    add(Calendar.MONTH, -1)
                }
                TimeIntervals.ONE_YEAR -> calendar.apply {
                    add(Calendar.YEAR, -1)
                }
                TimeIntervals.FIVE_YEAR -> calendar.apply {
                    add(Calendar.YEAR, -5)
                }
            }

            showLoading()
            getCoinPricesUseCase.execute(
                GetCoinPricesUseCase.Input(
                    coinId = coinId,
                    currency = COIN_PRICES_CURRENCY,
                    fromTimestamp = fromTimestamp.timeInMillis.div(1000),
                    toTimestamp = Calendar.getInstance().timeInMillis.div(1000)
                )
            ).catch { e ->
                hideLoading()
                _error.emit(e)
            }.filterNot {
                it.isEmpty()
            }.collect { coinPrices ->
                hideLoading()
                _coinPrices.emit(coinPrices)
            }
        }
}
