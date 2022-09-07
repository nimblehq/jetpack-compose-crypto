package co.nimblehq.compose.crypto.test

import co.nimblehq.compose.crypto.domain.model.CoinItem
import java.math.BigDecimal

object MockUtil {

    val myCoins = listOf(
        CoinItem(
            id = "bitcoin",
            symbol = "btc",
            coinName = "Bitcoin",
            image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
            currentPrice = BigDecimal(21953),
            marketCap = BigDecimal(418632879244),
            marketCapRank = 1,
            totalVolume = BigDecimal(40284988945),
            high24h = BigDecimal(23014),
            low24h = BigDecimal(21175),
            priceChange24h = BigDecimal(777.55),
            priceChangePercentage24h = 3.67201,
            marketCapChange24h = BigDecimal(15300446085.0),
            marketCapChangePercentage24h = 3.79351,
            priceChangePercentage24hInCurrency = 3.672009841642702
        )
    )
}
