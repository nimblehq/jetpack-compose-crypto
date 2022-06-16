package co.nimblehq.compose.crypto.ui.screens.home

import co.nimblehq.compose.crypto.databinding.FragmentHomeBinding
import co.nimblehq.compose.crypto.test.TestNavigatorModule.mockMainNavigator
import co.nimblehq.compose.crypto.test.getPrivateProperty
import co.nimblehq.compose.crypto.test.replace
import co.nimblehq.compose.crypto.ui.BaseFragmentTest
import dagger.hilt.android.testing.*
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.*

@HiltAndroidTest
class HomeFragmentTest : BaseFragmentTest<HomeFragment, FragmentHomeBinding>() {

    private val mockViewModel = mockk<HomeViewModel>(relaxed = true)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When initializing HomeFragment, its views display the text correctly`() {
        launchFragment()
        fragment.binding.btNext.text.toString() shouldBe "Next"
        fragment.binding.btCompose.text.toString() shouldBe "Jetpack Compose"
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<HomeFragment>(
            onInstantiate = {
                replace(getPrivateProperty("viewModel"), mockViewModel)
                navigator = mockMainNavigator
            }
        ) {
            fragment = this
            fragment.navigator.shouldNotBeNull()
        }
    }
}
