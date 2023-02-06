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

        composeAndroidTestRule.activity.setContent {
            DetailScreen(
                viewModel = viewModel,
                navigator = { destination -> appDestination = destination },
                coinId = "Bitcoin"
            )
        }
    }

    @Test
    fun `When enter to DetailScreen, it shows the Loading properly`() {
        initViewModel()

        composeAndroidTestRule.onNodeWithTag(
            testTag = TestTagDetailCircularProgress
        ).assertIsDisplayed()
    }

    @Test
    fun `When enter to DetailScreen and GetCoinDetail successfully, it renders the HeaderDetail properly`() {
        initViewModel()

        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagDetailAppbarTitle).assertTextEquals(coinDetailUiModel.coinName)
            onNodeWithTag(testTag = TestTagDetailLogo).assertIsDisplayed()
            onNodeWithTag(testTag = TestTagDetailCurrentPrice).assertTextEquals(
                "$${coinDetailUiModel.currentPrice.toFormattedString()}"
            )
            onNodeWithTag(testTag = TestTagDetailPriceChangePercent).assertTextEquals(
                "${abs(coinDetailUiModel.priceChangePercentage24hInCurrency).toFormattedString()}%"
            )
        }
    }

    @Test
    fun `When enter to DetailScreen and GetCoinDetail successfully, it renders the Chart properly`() {
        initViewModel()

        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagDetailLineChart).assertExists()
            onNodeWithTag(testTag = TestTagDetailChartInterval).assertExists()
        }
    }

    @Test
    fun `When enter to DetailScreen and GetCoinDetail successfully, it renders the CoinInfo properly`() {
        initViewModel()

        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagDetailCoinInfo).assertExists()

            with(onAllNodesWithTag(testTag = TestTagDetailItemTitle)) {
                onFirst().assertTextEquals(marketCapTitle)
                this[1].assertTextEquals(allTimeHighTitle)
                onLast().assertTextEquals(allTimeLowTitle)
            }

            with(onAllNodesWithTag(testTag = TestTagDetailItemPrice)) {
                onFirst().assertTextEquals("$${coinDetailUiModel.marketCap.toFormattedString()}")
                this[1].assertTextEquals("$${coinDetailUiModel.ath.toFormattedString()}")
                onLast().assertTextEquals("$${coinDetailUiModel.atl.toFormattedString()}")
            }

            with(onAllNodesWithTag(
                testTag = TestTagDetailItemPriceChange
            )) {
                onFirst().onChild().assertTextEquals(
                    "${abs(coinDetailUiModel.marketCapChangePercentage24h).toFormattedString()}%"
                )
                this[1].onChild().assertTextEquals(
                    "${abs(coinDetailUiModel.athChangePercentage).toFormattedString()}%"
                )
                onLast().onChild().assertTextEquals(
                    "${abs(coinDetailUiModel.atlChangePercentage).toFormattedString()}%"
                )
            }
        }
    }

    @Test
    fun `When enter to DetailScreen and GetCoinDetail successfully, it renders the SellBuyGroup properly`() {
        initViewModel()

        composeAndroidTestRule.onNodeWithTag(testTag = TestTagDetailSellBuyGroup).assertExists()
    }

    @Test
    fun `When enter to DetailScreen and GetCoinDetail failed, it shows the Toast properly`() {
        every { mockGetCoinDetailUseCase.execute(any()) } returns flow {
            throw Throwable(errorGeneric)
        }

        initViewModel()

        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagDetailAppbarTitle).assertDoesNotExist()
            onNodeWithTag(testTag = TestTagDetailLogo).assertDoesNotExist()
            onNodeWithTag(testTag = TestTagDetailCurrentPrice).assertDoesNotExist()
            onNodeWithTag(testTag = TestTagDetailPriceChangePercent).assertDoesNotExist()
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
