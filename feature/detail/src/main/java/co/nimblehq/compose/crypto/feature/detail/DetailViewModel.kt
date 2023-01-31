package co.nimblehq.compose.crypto.feature.detail

import co.nimblehq.compose.crypto.core.*
import co.nimblehq.compose.crypto.core.common.FIAT_CURRENCY
import co.nimblehq.compose.crypto.core.components.chartintervals.TimeIntervals
import co.nimblehq.compose.crypto.core.uimodel.CoinDetailUiModel
import co.nimblehq.compose.crypto.core.util.DispatchersProvider
import co.nimblehq.compose.crypto.domain.model.CoinPrice
import co.nimblehq.compose.crypto.domain.usecase.GetCoinDetailUseCase
import co.nimblehq.compose.crypto.domain.usecase.GetCoinPricesUseCase
import co.nimblehq.compose.crypto.feature.detail.util.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

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
                _error.emit(e)
            }
            .collect { coinDetail ->
                _coinDetailUiModel.emit(coinDetail.toUiModel())
            }
        hideLoading()
    }

    @Suppress("MagicNumber")
    fun getCoinPrices(coinId: String, timeIntervals: TimeIntervals = TimeIntervals.ONE_DAY) =
        execute {
            val fromTimestamp = Calendar.getInstance().apply {
                when (timeIntervals) {
                    TimeIntervals.ONE_DAY -> add(Calendar.DAY_OF_YEAR, -1)
                    TimeIntervals.ONE_WEEK -> add(Calendar.DAY_OF_YEAR, -7)
                    TimeIntervals.ONE_MONTH -> add(Calendar.MONTH, -1)
                    TimeIntervals.ONE_YEAR -> add(Calendar.YEAR, -1)
                    TimeIntervals.FIVE_YEAR -> add(Calendar.YEAR, -5)
                }
            }

            showLoading()
            getCoinPricesUseCase.execute(
                GetCoinPricesUseCase.Input(
                    coinId = coinId,
                    currency = co.nimblehq.compose.crypto.feature.home.FIAT_CURRENCY,
                    fromTimestamp = fromTimestamp.timeInMillis.div(1000),
                    toTimestamp = Calendar.getInstance().timeInMillis.div(1000)
                )
            ).catch { e ->
                _error.emit(e)
            }.filterNot {
                it.isEmpty()
            }.collect { coinPrices ->
                _coinPrices.emit(coinPrices)
            }
            hideLoading()
        }
}
