package co.nimblehq.compose.crypto.extension

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsEffect(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend (T) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        onEach(block).flowOn(context).launchIn(this)
    }
}
