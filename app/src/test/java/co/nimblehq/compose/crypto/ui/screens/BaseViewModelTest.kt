package co.nimblehq.compose.crypto.ui.screens

import co.nimblehq.compose.crypto.test.CoroutineTestRule
import co.nimblehq.compose.crypto.test.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest {

    @get:Rule
    private var coroutineRule = CoroutineTestRule()

    protected fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        coroutineRule.runBlockingTest(block)

    protected val testDispatcherProvider = coroutineRule.testDispatcherProvider

    protected val testDispatcher = coroutineRule.testDispatcher
}
