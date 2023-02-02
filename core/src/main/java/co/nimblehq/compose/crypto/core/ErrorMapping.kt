package co.nimblehq.compose.crypto.core

import android.content.Context

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is JsonApiException -> this.error.message
        else -> context.getString(R.string.error_generic)
    }
}
