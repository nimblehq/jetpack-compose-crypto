package co.nimblehq.compose.crypto.domain.model

import java.math.BigDecimal

data class CoinPrice(
    val timeStamp: Long,
    val price: BigDecimal
)
