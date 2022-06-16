package co.nimblehq.compose.crypto.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import co.nimblehq.common.extensions.visibleOrGone
import co.nimblehq.compose.crypto.databinding.FragmentHomeBinding
import co.nimblehq.compose.crypto.databinding.ViewLoadingBinding
import co.nimblehq.compose.crypto.extension.provideViewModels
import co.nimblehq.compose.crypto.lib.IsLoading
import co.nimblehq.compose.crypto.model.UserUiModel
import co.nimblehq.compose.crypto.ui.base.BaseFragment
import co.nimblehq.compose.crypto.ui.screens.MainNavigator
import co.nimblehq.compose.crypto.ui.screens.second.SecondBundle
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeViewModel by provideViewModels()

    private lateinit var viewLoadingBinding: ViewLoadingBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
    }

    override fun bindViewEvents() {
        super.bindViewEvents()

        with(binding) {
            btNext.setOnClickListener {
                viewModel.navigateToSecond(SecondBundle("From home"))
            }

            btCompose.setOnClickListener {
                viewModel.navigateToCompose()
            }
        }
    }

    override fun bindViewModel() {
        viewModel.userUiModels bindTo ::displayUsers
        viewModel.showLoading bindTo ::bindLoading
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
    }

    private fun displayUsers(userUiModels: List<UserUiModel>) {
        Timber.d("Result : $userUiModels")
    }

    private fun bindLoading(isLoading: IsLoading) {
        viewLoadingBinding.pbLoading.visibleOrGone(isLoading)
    }
}
