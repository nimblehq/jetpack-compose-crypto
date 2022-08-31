package co.nimblehq.compose.crypto.data.model.response

import co.nimblehq.compose.crypto.domain.model.CoinPrice
import com.squareup.moshi.Json

data class CoinPriceResponse(
    @Json(name = "prices")
    val prices: List<List<Double>>
)

fun CoinPriceResponse.toModel() = this.prices.map { response ->
    CoinPrice(
        timeStamp = (response[0].toLong()),
        price = (response[1] as? Double) ?: 0.0
    )
}
