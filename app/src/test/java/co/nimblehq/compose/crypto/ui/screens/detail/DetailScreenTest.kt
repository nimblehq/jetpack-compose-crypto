package co.nimblehq.compose.crypto.ui.screens.detail

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.*
import androidx.navigation.*
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.domain.usecase.GetCoinDetailUseCase
import co.nimblehq.compose.crypto.domain.usecase.GetCoinPricesUseCase
import co.nimblehq.compose.crypto.extension.toFormattedString
import co.nimblehq.compose.crypto.test.MockUtil
import co.nimblehq.compose.crypto.ui.components.chartintervals.TimeIntervals
import co.nimblehq.compose.crypto.ui.navigation.AppDestination
import co.nimblehq.compose.crypto.ui.screens.BaseViewModelTest
import co.nimblehq.compose.crypto.ui.screens.MainActivity
import co.nimblehq.compose.crypto.ui.uimodel.toUiModel
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast
import kotlin.math.abs

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class DetailScreenTest : BaseViewModelTest() {

    @get:Rule
    val composeAndroidTestRule = createAndroidComposeRule<MainActivity>()

    private val marketCapTitle: String
        get() = composeAndroidTestRule.activity.getString(R.string.detail_market_cap_title)

    private val allTimeHighTitle: String
        get() = composeAndroidTestRule.activity.getString(R.string.detail_all_time_high_title)

    private val allTimeLowTitle: String
        get() = composeAndroidTestRule.activity.getString(R.string.detail_all_time_low_title)

    private val errorGeneric: String
        get() = composeAndroidTestRule.activity.getString(R.string.error_generic)

    private val mockGetCoinDetailUseCase = mockk<GetCoinDetailUseCase>()
    private val mockGetCoinPricesUseCase = mockk<GetCoinPricesUseCase>()

    private lateinit var viewModel: DetailViewModel

    private var appDestination: AppDestination? = null

    private val coinDetailUiModel = MockUtil.coinDetail.toUiModel()

    @Before
    fun setUp() {
        every { mockGetCoinDetailUseCase.execute(any()) } returns flowOf(MockUtil.coinDetail)
        every { mockGetCoinPricesUseCase.execute(any()) } returns flowOf(emptyList())

        initViewModel()

        composeAndroidTestRule.activity.setContent {
            DetailScreen(
                viewModel = viewModel,
                navigator = { destination -> appDestination = destination },
                coinId = "Bitcoin"
            )
        }
    }

    @Test
    fun `When entering to the DetailScreen, it shows the loading properly`() {
        every { mockGetCoinDetailUseCase.execute(any()) } returns flow { delay(100) }

        composeAndroidTestRule.onNodeWithTag(
            testTag = TestTagDetailCircularProgress
        ).assertIsDisplayed()
    }

    @Test
    fun `When entering to the DetailScreen and GetCoinDetail successfully, it renders the HeaderDetail properly`() {
        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagDetailLogo).assertIsDisplayed()

            onNodeWithText(coinDetailUiModel.coinName).assertIsDisplayed()
            onNodeWithText(
                "$${coinDetailUiModel.currentPrice.toFormattedString()}"
            ).assertIsDisplayed()
            onNodeWithText(
                "${abs(coinDetailUiModel.priceChangePercentage24hInCurrency).toFormattedString()}%"
            ).assertIsDisplayed()
        }
    }

    @Test
    fun `When entering to the DetailScreen and GetCoinDetail successfully, it renders the Chart properly`() {
        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagDetailLineChart).assertExists()
            onNodeWithTag(testTag = TestTagDetailChartInterval).assertExists()

            onNodeWithText(TimeIntervals.ONE_DAY.text).assertIsDisplayed().assertHasClickAction()
            onNodeWithText(TimeIntervals.ONE_WEEK.text).assertIsDisplayed().assertHasClickAction()
            onNodeWithText(TimeIntervals.ONE_MONTH.text).assertIsDisplayed().assertHasClickAction()
            onNodeWithText(TimeIntervals.ONE_YEAR.text).assertIsDisplayed().assertHasClickAction()
            onNodeWithText(TimeIntervals.FIVE_YEAR.text).assertIsDisplayed().assertHasClickAction()
        }
    }

    @Test
    fun `When entering to the DetailScreen and GetCoinDetail successfully, it renders the CoinInfo properly`() {
        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagDetailCoinInfo).assertExists()

            onNodeWithText(marketCapTitle).assertIsDisplayed()
            onNodeWithText(allTimeHighTitle).assertIsDisplayed()
            onNodeWithText(allTimeLowTitle).assertIsDisplayed()

            onNodeWithText("$${coinDetailUiModel.marketCap.toFormattedString()}").assertIsDisplayed()
            onNodeWithText("$${coinDetailUiModel.ath.toFormattedString()}").assertIsDisplayed()
            onNodeWithText("$${coinDetailUiModel.atl.toFormattedString()}").assertIsDisplayed()

            onNodeWithText(
                "${abs(coinDetailUiModel.marketCapChangePercentage24h).toFormattedString()}%"
            ).assertIsDisplayed()
            onNodeWithText(
                "${abs(coinDetailUiModel.athChangePercentage).toFormattedString()}%"
            ).assertIsDisplayed()
            onNodeWithText(
                "${abs(coinDetailUiModel.atlChangePercentage).toFormattedString()}%"
            ).assertIsDisplayed()
        }
    }

    @Test
    fun `When entering to the DetailScreen and GetCoinDetail successfully, it renders the SellBuyGroup properly`() {
        composeAndroidTestRule.onNodeWithTag(testTag = TestTagDetailSellBuyGroup).assertExists()
    }

    @Test
    fun `When entering to the DetailScreen and GetCoinDetail failed, it shows the error message properly`() {
        every { mockGetCoinDetailUseCase.execute(any()) } returns flow {
            throw Throwable(errorGeneric)
        }

        with(composeAndroidTestRule) {
            onNodeWithText(coinDetailUiModel.coinName).assertDoesNotExist()
            onNodeWithText(
                "$${coinDetailUiModel.currentPrice.toFormattedString()}"
            ).assertDoesNotExist()
            onNodeWithText(
                "${abs(coinDetailUiModel.priceChangePercentage24hInCurrency).toFormattedString()}%"
            ).assertDoesNotExist()

            onNodeWithTag(testTag = TestTagDetailLogo).assertDoesNotExist()
            onNodeWithTag(testTag = TestTagDetailLineChart).assertDoesNotExist()
            onNodeWithTag(testTag = TestTagDetailChartInterval).assertDoesNotExist()
            onNodeWithTag(testTag = TestTagDetailCoinInfo).assertDoesNotExist()
            onNodeWithTag(testTag = TestTagDetailSellBuyGroup).assertDoesNotExist()
        }

        ShadowToast.getTextOfLatestToast() shouldBe errorGeneric
    }

    private fun initViewModel() {
        viewModel = DetailViewModel(
            dispatchers = testDispatcherProvider,
            getCoinDetailUseCase = mockGetCoinDetailUseCase,
            getCoinPricesUseCase = mockGetCoinPricesUseCase
        )
    }
}
