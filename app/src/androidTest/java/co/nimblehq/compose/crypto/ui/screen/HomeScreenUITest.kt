package co.nimblehq.compose.crypto.ui.screen

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.nimblehq.compose.crypto.test.MockUtil
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.domain.usecase.GetMyCoinsUseCase
import co.nimblehq.compose.crypto.domain.usecase.GetTrendingCoinsUseCase
import co.nimblehq.compose.crypto.extension.toFormattedString
import co.nimblehq.compose.crypto.test.TestDispatchersProvider
import co.nimblehq.compose.crypto.ui.navigation.AppDestination
import co.nimblehq.compose.crypto.ui.screens.MainActivity
import co.nimblehq.compose.crypto.ui.screens.home.*
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenUITest {

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

        every { mockGetMyCoinsUseCase.execute(any()) } returns flowOf(MockUtil.myCoins)
        every { mockGetTrendingCoinsUseCase.execute(any()) } returns flowOf(MockUtil.trendingCoins)
    }

    @Test
    fun when_entering_HomeScreen__it_renders_the_PortfolioCard_properly() {
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
    fun when_loading_MyCoins__it_renders_the_LoadingProgress_properly() {
        every { mockGetMyCoinsUseCase.execute(any()) } returns flow { delay(500) }

        initViewModel()

        composeAndroidTestRule.onNodeWithTag(testTag = TestTagCoinsLoader).assertIsDisplayed()
    }

    @Test
    fun when_loading_TrendingCoins__it_renders_the_LoadingProgress_properly() {
        every { mockGetTrendingCoinsUseCase.execute(any()) } returns flow { delay(500) }

        initViewModel()

        composeAndroidTestRule.onNodeWithTag(testTag = TestTagCoinsLoader).assertIsDisplayed()
    }

    @Test
    fun when_entering_HomeScreen_and_loading_MyCoins_successfully__it_renders_the_UI_properly() {
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
        }
    }

    @Test
    fun when_entering_to_the_HomeScreen_and_loading_TrendingCoins_successfully__it_renders_the_UI_properly() {
        initViewModel()

        with(composeAndroidTestRule) {
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
    fun when_clicked_on_MyCoin_item__it_navigates_to_DetailScreen() {
        initViewModel()

        composeAndroidTestRule.onAllNodesWithTag(testTag = TestTagCoinItem).onFirst().performClick()

        appDestination shouldBe AppDestination.CoinDetail
    }

    @Test
    fun when_clicked_on_TrendingCoin_item__it_navigates_to_DetailScreen() {
        initViewModel()

        composeAndroidTestRule.onAllNodesWithTag(
            testTag = TestTagTrendingItem
        ).onFirst().performClick()

        appDestination shouldBe AppDestination.CoinDetail
    }

    @Test
    fun when_entering_to_the_HomeScreen_and_loading_MyCoins_failed__it_shows_the_error_message() {
        every { mockGetMyCoinsUseCase.execute(any()) } returns flow {
            throw Throwable(errorGeneric)
        }

        initViewModel()

        composeAndroidTestRule.onNodeWithTag(
            testTag = TestTagCoinItem,
            useUnmergedTree = true
        ).assertDoesNotExist()

        // TODO: Add the assertion for the error message
    }

    @Test
    fun when_entering_to_the_HomeScreen_and_loading_TrendingCoins_failed__it_shows_the_error_message() {
        every { mockGetTrendingCoinsUseCase.execute(any()) } returns flow {
            throw Throwable(errorGeneric)
        }

        initViewModel()

        composeAndroidTestRule.onNodeWithTag(
            testTag = TestTagTrendingItem,
            useUnmergedTree = true
        ).assertDoesNotExist()

        // TODO: Add the assertion for the error message
    }

    private fun initViewModel() {
        viewModel = HomeViewModel(
            dispatchers = TestDispatchersProvider,
            getMyCoinsUseCase = mockGetMyCoinsUseCase,
            getTrendingCoinsUseCase = mockGetTrendingCoinsUseCase
        )
    }
}
