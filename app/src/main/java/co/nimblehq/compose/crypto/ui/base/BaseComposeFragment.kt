package co.nimblehq.compose.crypto.ui.base

import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import co.nimblehq.compose.crypto.ui.common.Toaster
import co.nimblehq.compose.crypto.ui.userReadableMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Deprecated("unused")
abstract class BaseComposeFragment : Fragment(), BaseComposeFragmentCallbacks {

    @Inject
    lateinit var toaster: Toaster

    protected abstract val composeScreen: @Composable () -> Unit

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this as? BaseComposeFragmentCallbacks)?.let { initViewModel() }
    }

    override fun initViewModel() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent { composeScreen.invoke() }
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (this as? BaseComposeFragmentCallbacks)?.let {
            bindViewModel()
        }
    }

    open fun displayError(error: Throwable) {
        val message = error.userReadableMessage(requireContext())
        toaster.display(message)
    }

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit) {
        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    collect { action(it) }
                }
            }
        }
    }
}
