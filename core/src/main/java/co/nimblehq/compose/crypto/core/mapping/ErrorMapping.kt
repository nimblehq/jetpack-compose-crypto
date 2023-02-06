package co.nimblehq.compose.crypto.core.mapping

import android.content.Context
import co.nimblehq.compose.crypto.core.JsonApiException
import co.nimblehq.compose.crypto.core.R

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is JsonApiException -> this.error.message
        else -> context.getString(R.string.error_generic)
    }
}
