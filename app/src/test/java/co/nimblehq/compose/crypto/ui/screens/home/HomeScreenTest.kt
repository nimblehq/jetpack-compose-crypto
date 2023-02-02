package co.nimblehq.compose.crypto.ui.screens.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.*
import androidx.navigation.*
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.core.extension.toFormattedString
import co.nimblehq.compose.crypto.core.navigation.AppDestination
import co.nimblehq.compose.crypto.domain.usecase.GetMyCoinsUseCase
import co.nimblehq.compose.crypto.domain.usecase.GetTrendingCoinsUseCase
import co.nimblehq.compose.crypto.feature.home.*
import co.nimblehq.compose.crypto.test.MockUtil
import co.nimblehq.compose.crypto.ui.screens.BaseViewModelTest
import co.nimblehq.compose.crypto.ui.screens.MainActivity
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class HomeScreenTest : BaseViewModelTest() {

    @get:Rule
    val composeAndroidTestRule = createAndroidComposeRule<MainActivity>()

    private val homeTitle: String
        get() = composeAndroidTestRule.activity.getString(R.string.home_title)

    private val totalCoinsLabel: String
        get() = composeAndroidTestRule.activity.getString(R.string.portfolio_card_total_coin_label)

    private val todayProfitLabel: String
        get() = composeAndroidTestRule.activity.getString(R.string.portfolio_card_today_profit_label)

    private val errorGeneric: String
        get() = composeAndroidTestRule.activity.getString(R.string.error_generic)

    private val expectedPriceChange: String
        get() = composeAndroidTestRule.activity.getString(
            R.string.coin_profit_percent,
            MockUtil.trendingCoins.first().priceChangePercentage24hInCurrency.toFormattedString()
        )

    private val mockGetMyCoinsUseCase = mockk<GetMyCoinsUseCase>()
    private val mockGetTrendingCoinsUseCase = mockk<GetTrendingCoinsUseCase>()

    private lateinit var viewModel: HomeViewModel

    private var appDestination: AppDestination? = null

    @Before
    fun setUp() {
        composeAndroidTestRule.activity.setContent {
            HomeScreen(
                viewModel = viewModel,
                navigator = { destination -> appDestination = destination }
            )
        }
    }

    @Test
    fun `When enter to HomeScreen, it render the PortfolioCard properly`() {
        initViewModel()

        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagHomeTitle).assertTextEquals(homeTitle)
            onNodeWithTag(testTag = TestTagTotalCoinsLabel).assertTextEquals(totalCoinsLabel)
            onNodeWithTag(testTag = TestTagTodayCoinProfitLabel).assertTextEquals(todayProfitLabel)
            onNodeWithTag(testTag = TestTagCardTotalCoins).assertTextEquals("$7,273,291")
            onNodeWithTag(testTag = TestTagCardTodayProfit).assertTextEquals("$193,280")
        }
    }

    @Test
    fun `When enter to HomeScreen and load MyCoins successfully, it render the UI properly`() {
        every { mockGetMyCoinsUseCase.execute(any()) } returns flowOf(MockUtil.myCoins)

        initViewModel()

        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagCoinsLoader).assertIsDisplayed()

            with(MockUtil.myCoins.first()) {
                onAllNodesWithTag(
                    testTag = TestTagCoinItemSymbol,
                    useUnmergedTree = true
                ).onFirst().assertTextEquals(symbol.uppercase())

                onAllNodesWithTag(
                    testTag = TestTagCoinItemCoinName,
                    useUnmergedTree = true
                ).onFirst().assertTextEquals(coinName)

                onAllNodesWithTag(
                    testTag = TestTagCoinItemPriceChange,
                    useUnmergedTree = true
                ).onFirst().onChild().assertTextEquals(expectedPriceChange)
            }
        }
    }

    @Test
    fun `When enter to HomeScreen and load TrendingCoins successfully, it render the UI properly`() {
        every { mockGetTrendingCoinsUseCase.execute(any()) } returns flowOf(MockUtil.trendingCoins)

        initViewModel()

        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagCoinsLoader).assertIsDisplayed()

            with(MockUtil.trendingCoins.first()) {
                onAllNodesWithTag(
                    testTag = TestTagTrendingItemSymbol,
                    useUnmergedTree = true
                ).onFirst().assertTextEquals(symbol.uppercase())

                onAllNodesWithTag(
                    testTag = TestTagTrendingItemCoinName,
                    useUnmergedTree = true
                ).onFirst().assertTextEquals(coinName)

                onAllNodesWithTag(
                    testTag = TestTagTrendingItemPriceChange,
                    useUnmergedTree = true
                ).onFirst().onChild().assertTextEquals(expectedPriceChange)
            }
        }
    }

    @Test
    fun `When clicked on MyCoin item, it navigates to DetailScreen`() {
        every { mockGetMyCoinsUseCase.execute(any()) } returns flowOf(MockUtil.myCoins)

        initViewModel()

        composeAndroidTestRule.onAllNodesWithTag(testTag = TestTagCoinItem).onFirst().performClick()

        appDestination shouldBe AppDestination.CoinDetail
    }

    @Test
    fun `When clicked on TrendingCoin item, it navigates to DetailScreen`() {
        every { mockGetTrendingCoinsUseCase.execute(any()) } returns flowOf(MockUtil.trendingCoins)

        initViewModel()

        composeAndroidTestRule.onAllNodesWithTag(
            testTag = TestTagTrendingItem
        ).onFirst().performClick()

        appDestination shouldBe AppDestination.CoinDetail
    }

    @Test
    fun `When enter to HomeScreen and load MyCoins failed, it shows the Toast properly`() {
        every { mockGetMyCoinsUseCase.execute(any()) } returns flow {
            throw Throwable(errorGeneric)
        }

        initViewModel()

        composeAndroidTestRule.onNodeWithTag(
            testTag = TestTagCoinItem,
            useUnmergedTree = true
        ).assertDoesNotExist()

        ShadowToast.getTextOfLatestToast() shouldBe errorGeneric
    }

    @Test
    fun `When enter to HomeScreen and load TrendingCoins failed, it shows the Toast properly`() {
        every { mockGetTrendingCoinsUseCase.execute(any()) } returns flow {
            throw Throwable(errorGeneric)
        }

        initViewModel()

        composeAndroidTestRule.onNodeWithTag(
            testTag = TestTagTrendingItem,
            useUnmergedTree = true
        ).assertDoesNotExist()

        ShadowToast.getTextOfLatestToast() shouldBe errorGeneric
    }

    @Test
    fun `When pulled to refresh and load MyCoins successfully, it render the UI properly`() {
        every { mockGetMyCoinsUseCase.execute(any()) } returns flowOf(MockUtil.myCoins)

        initViewModel()

        with(composeAndroidTestRule) {
            with(MockUtil.myCoins.first()) {
                onAllNodesWithTag(
                    testTag = TestTagCoinItemSymbol,
                    useUnmergedTree = true
                ).onFirst().assertTextEquals(symbol.uppercase())

                onAllNodesWithTag(
                    testTag = TestTagCoinItemCoinName,
                    useUnmergedTree = true
                ).onFirst().assertTextEquals(coinName)

                onAllNodesWithTag(
                    testTag = TestTagCoinItemPriceChange,
                    useUnmergedTree = true
                ).onFirst().onChild().assertTextEquals(expectedPriceChange)
            }

            onRoot().performTouchInput { swipeDown() }
        }

        verify(exactly = 2) { mockGetMyCoinsUseCase.execute(any()) }
    }

    @Test
    fun `When pulled to refresh and load TrendingCoins successfully, it render the UI properly`() {
        every { mockGetTrendingCoinsUseCase.execute(any()) } returns flowOf(MockUtil.trendingCoins)

        initViewModel()

        with(composeAndroidTestRule) {
            onNodeWithTag(testTag = TestTagCoinsLoader).assertIsDisplayed()

            with(MockUtil.trendingCoins.first()) {
                onAllNodesWithTag(
                    testTag = TestTagTrendingItemSymbol,
                    useUnmergedTree = true
                ).onFirst().assertTextEquals(symbol.uppercase())

                onAllNodesWithTag(
                    testTag = TestTagTrendingItemCoinName,
                    useUnmergedTree = true
                ).onFirst().assertTextEquals(coinName)

                onAllNodesWithTag(
                    testTag = TestTagTrendingItemPriceChange,
                    useUnmergedTree = true
                ).onFirst().onChild().assertTextEquals(expectedPriceChange)
            }

            onRoot().performTouchInput { swipeDown() }
        }

        verify(exactly = 5) { mockGetTrendingCoinsUseCase.execute(any()) }
    }

    private fun initViewModel() {
        viewModel = HomeViewModel(
            dispatchers = testDispatcherProvider,
            getMyCoinsUseCase = mockGetMyCoinsUseCase,
            getTrendingCoinsUseCase = mockGetTrendingCoinsUseCase
        )
    }
}
