package co.nimblehq.compose.crypto.ui.screens.second

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import co.nimblehq.compose.crypto.databinding.FragmentSecondBinding
import co.nimblehq.compose.crypto.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding>() {

    private val viewModel by viewModels<SecondViewModel>()

    private val args: SecondFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSecondBinding
        get() = { inflater, container, attachToParent ->
            FragmentSecondBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        binding.tvMessage.text = args.bundle.message
    }

    override fun bindViewModel() {}
}
