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
            fullyDilutedValuation = BigDecimal(394474286491),
            totalVolume = BigDecimal(40284988945),
            high24h = BigDecimal(23014),
            low24h = BigDecimal(21175),
            priceChange24h = BigDecimal(777.55),
            priceChangePercentage24h = 3.67201,
            marketCapChange24h = BigDecimal(15300446085.0),
            marketCapChangePercentage24h = 3.79351,
            circulatingSupply = BigDecimal(19143668),
            totalSupply = BigDecimal(21000000),
            maxSupply = BigDecimal(21000000),
            ath = BigDecimal(69045),
            athChangePercentage = -68.93253,
            athDate = "2021-11-10T14:24:19.604Z",
            atl = BigDecimal(0.0398177),
            atlChangePercentage = 661256.26362,
            atlDate = "2017-10-19T00:00:00.000Z",
            roi = CoinItem.RoiItem(
                times = BigDecimal(106.82921216576392),
                currency = "btc",
                percentage = 10682.921216576393
            ),
            lastUpdated = "2022-09-07T05:38:22.556Z",
            priceChangePercentage24hInCurrency = 3.672009841642702
        )
    )
}
