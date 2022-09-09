package co.nimblehq.compose.crypto.ui

import android.content.Context
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.data.service.JsonApiException

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is JsonApiException -> this.error.message
        else -> context.getString(R.string.error_generic)
    }
}
