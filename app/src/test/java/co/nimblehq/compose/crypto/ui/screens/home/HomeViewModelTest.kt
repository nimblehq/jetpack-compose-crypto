package co.nimblehq.compose.crypto.ui.screens.home

import app.cash.turbine.test
import co.nimblehq.compose.crypto.domain.usecase.*
import co.nimblehq.compose.crypto.test.MockUtil
import co.nimblehq.compose.crypto.ui.navigation.AppDestination
import co.nimblehq.compose.crypto.ui.screens.BaseViewModelTest
import co.nimblehq.compose.crypto.ui.uimodel.toUiModel
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseViewModelTest() {

    private val mockGetMyCoinsUseCase = mockk<GetMyCoinsUseCase>()
    private val mockGetTrendingCoinsUseCase = mockk<GetTrendingCoinsUseCase>()
    private lateinit var viewModel: HomeViewModel

    // TODO remove in integration ticket
    private val mockIsNetworkConnectedUseCase = mockk<IsNetworkConnectedUseCase>()

    @Before
    fun setUp() {
        every { mockGetMyCoinsUseCase.execute(any()) } returns flowOf(MockUtil.myCoins)
        every { mockGetTrendingCoinsUseCase.execute(any()) } returns flowOf(MockUtil.trendingCoins)

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `When getting my coin list successfully, it should return my coin list`() =
        runBlockingTest {
            testDispatcher.pauseDispatcher()
            initViewModel()
            val expected = MockUtil.myCoins.map { it.toUiModel() }
            viewModel.output.myCoins.test {
                testDispatcher.resumeDispatcher()
                expectMostRecentItem() shouldBe expected
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `When getting my coin list failed, it should throw error`() =
        runBlockingTest {
            testDispatcher.pauseDispatcher()
            initViewModel()
            val error = Throwable()
            every { mockGetMyCoinsUseCase.execute(any()) } returns flow { throw error }

            viewModel.output.myCoinsError.test {
                testDispatcher.resumeDispatcher()
                expectMostRecentItem() shouldBe error
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `When getting trending coin list successfully, it should return my coin list`() =
        runBlockingTest {
            testDispatcher.pauseDispatcher()
            initViewModel()
            val expected = MockUtil.trendingCoins.map { it.toUiModel() }
            viewModel.output.trendingCoins.test {
                testDispatcher.resumeDispatcher()
                expectMostRecentItem() shouldBe expected
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `When getting trending coin list failed, it should throw error`() =
        runBlockingTest {
            testDispatcher.pauseDispatcher()
            initViewModel()
            val error = Throwable()
            every { mockGetTrendingCoinsUseCase.execute(any()) } returns flow { throw error }

            viewModel.output.trendingCoinsError.test {
                testDispatcher.resumeDispatcher()
                expectMostRecentItem() shouldBe error
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `When clicking on My Coins item, it navigates to Coin Detail screen`() =
        runBlockingTest {
            initViewModel()
            val coin = MockUtil.myCoins[0].toUiModel()
            viewModel.output.navigator.test {
                viewModel.input.onMyCoinsItemClick(coin)
                expectMostRecentItem() shouldBe AppDestination.CoinDetail.buildDestination(coin.id)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `When clicking on Trending Coins item, it navigates to Coin Detail screen`() =
        runBlockingTest {
            initViewModel()
            val coin = MockUtil.myCoins[0].toUiModel()
            viewModel.output.navigator.test {
                viewModel.input.onTrendingCoinsItemClick(coin)
                expectMostRecentItem() shouldBe AppDestination.CoinDetail.buildDestination(coin.id)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `When calling clearMyCoinsError, it should reset myCoinsError to null`() =
        runBlockingTest {
            initViewModel()
            viewModel.output.myCoinsError.test {
                viewModel.input.clearMyCoinsError()
                expectMostRecentItem() shouldBe null
            }
        }

    @Test
    fun `When calling clearTrendingCoinsError, it should reset trendingCoinsError to null`() =
        runBlockingTest {
            initViewModel()
            viewModel.output.trendingCoinsError.test {
                viewModel.input.clearTrendingCoinsError()
                expectMostRecentItem() shouldBe null
            }
        }

    private fun initViewModel() {
        viewModel = HomeViewModel(
            testDispatcherProvider,
            mockGetMyCoinsUseCase,
            mockGetTrendingCoinsUseCase,
            mockIsNetworkConnectedUseCase
        )
    }
}
