package co.nimblehq.compose.crypto.ui

import android.content.Context
import co.nimblehq.compose.crypto.R

fun Throwable.userReadableMessage(context: Context): String {
    // TODO implement user readable message
    return context.getString(R.string.error_generic)
}
