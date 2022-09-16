package co.nimblehq.compose.crypto.data.model.response

import co.nimblehq.compose.crypto.domain.model.CoinPrice
import com.squareup.moshi.Json
import java.math.BigDecimal

data class CoinPriceResponse(
    @Json(name = "prices")
    val prices: List<List<BigDecimal>>
)

internal fun CoinPriceResponse.toModel() = this.prices.map { response ->
    CoinPrice(
        timeStamp = (response[0].toLong()),
        price = response[1]
    )
}
