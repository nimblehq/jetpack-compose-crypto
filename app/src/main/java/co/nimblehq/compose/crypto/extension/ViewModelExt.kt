package co.nimblehq.compose.crypto.extension

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.provideViewModels(
    noinline factoryProducer: (() -> CreationExtras)? = null
): Lazy<VM> = OverridableLazy(viewModels(factoryProducer))

class OverridableLazy<T>(var implementation: Lazy<T>) : Lazy<T> {

    override val value
        get() = implementation.value

    override fun isInitialized() = implementation.isInitialized()
}
