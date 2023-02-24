package co.nimblehq.compose.crypto.test

import co.nimblehq.compose.crypto.domain.model.*
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

    val trendingCoins = myCoins

    val coinDetail = CoinDetail(
        id = "bitcoin",
        symbol = "btc",
        coinName = "Bitcoin",
        image = CoinDetail.Image(
            large = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
            small = "https://assets.coingecko.com/coins/images/1/small/bitcoin.png?1547033579",
            thumb = "https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png?1547033579"
        ),
        marketData = CoinDetail.MarketData(
            currentPrice = mapOf("usd" to BigDecimal(19112.45)),
            ath = mapOf("usd" to BigDecimal(69045)),
            athChangePercentage = mapOf("usd" to -72.30426),
            athDate = emptyMap(),
            atl = mapOf("usd" to BigDecimal(67.81)),
            atlChangePercentage = mapOf("usd" to 28100.4782),
            atlDate = emptyMap(),
            marketCap = mapOf("usd" to BigDecimal(366436890217)),
            marketCapRank = 0,
            fullyDilutedValuation = emptyMap(),
            totalVolume = emptyMap(),
            high24h = emptyMap(),
            low24h = emptyMap(),

            priceChange24h = BigDecimal.ZERO,
            priceChangePercentage24h = 0.0,
            priceChangePercentage7d = 0.0,
            priceChangePercentage14d = 0.0,
            priceChangePercentage30d = 0.0,
            priceChangePercentage60d = 0.0,
            priceChangePercentage200d = 0.0,
            priceChangePercentage1y = 0.0,
            marketCapChange24h = BigDecimal.ZERO,
            marketCapChangePercentage24h = 1.0166,

            priceChange24hInCurrency = emptyMap(),
            priceChangePercentage24hInCurrency = mapOf("usd" to 0.74874),
            priceChangePercentage7dInCurrency = emptyMap(),
            priceChangePercentage14dInCurrency = emptyMap(),
            priceChangePercentage30dInCurrency = emptyMap(),
            priceChangePercentage60dInCurrency = emptyMap(),
            priceChangePercentage200dInCurrency = emptyMap(),
            priceChangePercentage1yInCurrency = emptyMap(),
            marketCapChange24hInCurrency = emptyMap(),
            marketCapChangePercentage24hInCurrency = emptyMap(),

            totalSupply = BigDecimal.ZERO,
            maxSupply = BigDecimal.ZERO,
            circulatingSupply = BigDecimal.ZERO,

            lastUpdated = "lastUpdated"
        )
    )

    val coinPrices = listOf(
        CoinPrice(1000,BigDecimal.ZERO),
        CoinPrice(2000, BigDecimal.ONE)
    )
}
