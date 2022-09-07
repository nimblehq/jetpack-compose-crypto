package co.nimblehq.compose.crypto.extension

import java.text.DecimalFormat

fun Double.toFormattedString(
    format: String = "#,###",
    minDigit: Int = 0,
    maxDigit: Int = 2
): String {
    return try {
        DecimalFormat(format).apply {
            minimumIntegerDigits = 1
            minimumFractionDigits = minDigit
            maximumFractionDigits = maxDigit
        }.format(this)
    } catch (e: IllegalArgumentException) {
        ""
    }
}
