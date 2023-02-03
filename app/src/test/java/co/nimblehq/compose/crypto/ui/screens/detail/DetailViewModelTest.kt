package co.nimblehq.compose.crypto.ui.screens.detail

import app.cash.turbine.test
import co.nimblehq.compose.crypto.domain.detail.usecase.GetCoinDetailUseCase
import co.nimblehq.compose.crypto.domain.detail.usecase.GetCoinPricesUseCase
import co.nimblehq.compose.crypto.feature.detail.DetailViewModel
import co.nimblehq.compose.crypto.feature.detail.util.toUiModel
import co.nimblehq.compose.crypto.test.MockUtil
import co.nimblehq.compose.crypto.ui.screens.BaseViewModelTest
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
class DetailViewModelTest : BaseViewModelTest() {

    private val mockGetCoinDetailUseCase = mockk<GetCoinDetailUseCase>()
    private val mockGetCoinPricesUseCase = mockk<GetCoinPricesUseCase>()
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        every { mockGetCoinDetailUseCase.execute(any()) } returns flowOf(MockUtil.coinDetail)
        every { mockGetCoinPricesUseCase.execute(any()) } returns flowOf(emptyList())

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `When getting coin detail successfully, it should return my coin detail`() =
        runBlockingTest {
            testDispatcher.pauseDispatcher()
            initViewModel()
            val expected = MockUtil.coinDetail.toUiModel()

            viewModel.getCoinId("bitcoin")

            viewModel.output.coinDetailUiModel.test {
                testDispatcher.resumeDispatcher()
                expectMostRecentItem() shouldBe expected
            }
        }

    @Test
    fun `When getting coin detail failed, it should throw error`() =
        runBlockingTest {
            testDispatcher.pauseDispatcher()
            initViewModel()
            val error = Throwable()
            every { mockGetCoinDetailUseCase.execute(any()) } returns flow { throw error }

            viewModel.input.getCoinId("bitcoin")

            viewModel.output.error.test {
                testDispatcher.resumeDispatcher()
                expectMostRecentItem() shouldBe error
            }
        }

    private fun initViewModel() {
        viewModel = DetailViewModel(
            testDispatcherProvider,
            mockGetCoinDetailUseCase,
            mockGetCoinPricesUseCase
        )
    }
}
