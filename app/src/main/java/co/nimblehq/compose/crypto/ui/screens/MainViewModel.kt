package co.nimblehq.compose.crypto.ui.screens

import co.nimblehq.compose.crypto.ui.base.BaseViewModel
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// TODO: Consider to update or remove it on Integrate task.
@HiltViewModel
class MainViewModel @Inject constructor(dispatchers: DispatchersProvider) : BaseViewModel(dispatchers)
