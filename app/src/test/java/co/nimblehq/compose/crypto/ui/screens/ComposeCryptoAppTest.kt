package co.nimblehq.compose.crypto.ui.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.domain.usecase.IsNetworkConnectedUseCase
import co.nimblehq.compose.crypto.rememberCryptoAppState
import co.nimblehq.compose.crypto.ui.navigation.ComposeCryptoApp
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class ComposeCryptoAppTest : BaseViewModelTest() {

    @get:Rule
    val composeAndroidTestRule = createAndroidComposeRule<MainActivity>()

    private val mockIsNetworkConnectedUseCase: IsNetworkConnectedUseCase = mockk()

    private val noInternetMessage: String
        get() = composeAndroidTestRule.activity.getString(R.string.no_internet_message)

    @Before
    fun setUp() {
        composeAndroidTestRule.activity.setContent {
            ComposeCryptoApp(
                cryptoAppState = rememberCryptoAppState(
                    isNetworkConnectedUseCase = mockIsNetworkConnectedUseCase,
                    dispatchersProvider = testDispatcherProvider
                )
            )
        }
    }

    @Test
    fun `When there is no internet, it shows dialog`() {
        every { mockIsNetworkConnectedUseCase.invoke() } returns flowOf(false)

        with(composeAndroidTestRule) {
            onNodeWithText(noInternetMessage).assertIsDisplayed()
        }
    }

    @Test
    fun `When there is internet, it does not show dialog`() {
        every { mockIsNetworkConnectedUseCase.invoke() } returns flowOf(true)

        with(composeAndroidTestRule) {
            onNodeWithText(noInternetMessage).assertDoesNotExist()
        }
    }
}
