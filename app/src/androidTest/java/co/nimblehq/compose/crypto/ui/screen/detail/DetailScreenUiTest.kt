package co.nimblehq.compose.crypto.ui.screen.detail

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.domain.usecase.GetCoinDetailUseCase
import co.nimblehq.compose.crypto.domain.usecase.GetCoinPricesUseCase
import co.nimblehq.compose.crypto.extension.toFormattedString
import co.nimblehq.compose.crypto.test.MockUtil.coinDetail
import co.nimblehq.compose.crypto.test.MockUtil.coinPrices
import co.nimblehq.compose.crypto.test.TestDispatchersProvider
import co.nimblehq.compose.crypto.ui.components.chartintervals.TimeIntervals
import co.nimblehq.compose.crypto.ui.screens.MainActivity
import co.nimblehq.compose.crypto.ui.screens.detail.*
import co.nimblehq.compose.crypto.ui.screens.home.FIAT_CURRENCY
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.abs

@ExperimentalCoroutinesApi
class DetailScreenUiTest {

    @get:Rule
    val composeAndroidTestRule = createAndroidComposeRule<MainActivity>()

    private val errorGeneric: String
        get() = composeAndroidTestRule.activity.getString(R.string.error_generic)

    private val mockGetCoinDetailUseCase: GetCoinDetailUseCase = mockk()
    private val mockGetCoinPricesUseCase: GetCoinPricesUseCase = mockk()

    private lateinit var detailViewModel: DetailViewModel


    @Before
    fun setUp() {
        every { mockGetCoinDetailUseCase.execute(any()) } returns flowOf(coinDetail)
        every { mockGetCoinPricesUseCase.execute(any()) } returns flowOf(coinPrices)

        initViewModel()
        composeAndroidTestRule.activity.setContent {
            DetailScreen(
                coinId = "",
                viewModel = detailViewModel,
                navigator = { }
            )
        }
    }

    @Test
    fun when_navigating_to_detail_screen_it_shows_loading() {
        every { mockGetCoinDetailUseCase.execute(any()) } returns flow {
            delay(100L)
            emit(coinDetail)
        }
        every { mockGetCoinPricesUseCase.execute(any()) } returns flow {
            delay(100L)
            emit(coinPrices)
        }

        with(composeAndroidTestRule) {
            onNodeWithTag(TestTagDetailLoading).assertIsDisplayed()
        }
    }

    @Test
    fun when_navigating_to_detail_screen_it_renders_chart_interval_buttons_properly() {
        with(composeAndroidTestRule) {
            onNodeWithText(TimeIntervals.ONE_DAY.text).assertIsDisplayed()
            onNodeWithText(TimeIntervals.ONE_WEEK.text).assertIsDisplayed()
            onNodeWithText(TimeIntervals.ONE_MONTH.text).assertIsDisplayed()
            onNodeWithText(TimeIntervals.ONE_YEAR.text).assertIsDisplayed()
            onNodeWithText(TimeIntervals.FIVE_YEAR.text).assertIsDisplayed()
        }
    }

    @Test
    fun when_navigating_to_detail_screen_it_render_currentPrice_and_priceChangePercentage24hInCurrency_properly() {
        with(composeAndroidTestRule) {
            coinDetail.marketData?.let { marketData ->
                val currentPrice = "$${marketData.currentPrice[FIAT_CURRENCY]?.toFormattedString()}"
                onNodeWithText(currentPrice).assertIsDisplayed()

                val priceChangePercentage24hInCurrency = this.activity.getString(
                    R.string.coin_profit_percent,
                    abs(marketData.priceChangePercentage24hInCurrency[FIAT_CURRENCY] ?: 0.0).toFormattedString()
                )
                onNodeWithText(priceChangePercentage24hInCurrency).assertIsDisplayed()
            }
        }
    }

    @Test
    fun when_navigating_to_detail_screen_it_render_coin_info_properly() {
        with(composeAndroidTestRule) {
            coinDetail.marketData?.let { marketData ->
                val marketCap = "$${marketData.marketCap[FIAT_CURRENCY]?.toFormattedString()}"
                onNodeWithText(marketCap).assertIsDisplayed()

                val allTimeHigh = "$${marketData.ath[FIAT_CURRENCY]?.toFormattedString()}"
                onNodeWithText(allTimeHigh).assertIsDisplayed()

                val allTimeLow = "$${marketData.atl[FIAT_CURRENCY]?.toFormattedString()}"
                onNodeWithText(allTimeLow).assertIsDisplayed()
            }
        }
    }

    @Test
    fun when_navigating_to_detail_screen_and_has_api_error_coin_price_chart_is_not_displayed() {
        every { mockGetCoinDetailUseCase.execute(any()) } returns flow {
            throw Throwable(errorGeneric)
        }

        with(composeAndroidTestRule) {
            onNodeWithText(TestTagDetailCoinPriceChart).assertDoesNotExist()
        }
    }

    @Test
    fun when_navigating_to_detail_screen_chart_interval_buttons_are_clickable() {
        with(composeAndroidTestRule) {
            onNodeWithText(TimeIntervals.ONE_DAY.text).assertHasClickAction()
            onNodeWithText(TimeIntervals.ONE_WEEK.text).assertHasClickAction()
            onNodeWithText(TimeIntervals.ONE_MONTH.text).assertHasClickAction()
            onNodeWithText(TimeIntervals.ONE_YEAR.text).assertHasClickAction()
            onNodeWithText(TimeIntervals.FIVE_YEAR.text).assertHasClickAction()
        }
    }

    private fun initViewModel() {
        detailViewModel = DetailViewModel(
            dispatchers = TestDispatchersProvider,
            getCoinDetailUseCase = mockGetCoinDetailUseCase,
            getCoinPricesUseCase = mockGetCoinPricesUseCase
        )
    }
}
