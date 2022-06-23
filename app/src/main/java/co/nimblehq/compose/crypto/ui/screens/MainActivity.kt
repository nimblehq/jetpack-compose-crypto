package co.nimblehq.compose.crypto.ui.screens

import android.view.LayoutInflater
import androidx.activity.viewModels
import co.nimblehq.compose.crypto.databinding.ActivityMainBinding
import co.nimblehq.compose.crypto.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = { inflater -> ActivityMainBinding.inflate(inflater) }

    override val viewModel by viewModels<MainViewModel>()
}
