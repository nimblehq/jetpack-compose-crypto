package co.nimblehq.compose.crypto.data.extension

import java.math.BigDecimal

fun BigDecimal?.orZero(): BigDecimal = this ?: BigDecimal.ZERO
fun Double?.orZero(): Double = this ?: 0.0
fun Int?.orZero(): Int = this ?: 0
