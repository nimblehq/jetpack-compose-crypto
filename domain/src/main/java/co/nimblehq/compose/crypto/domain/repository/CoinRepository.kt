package co.nimblehq.compose.crypto.domain.repository

import co.nimblehq.compose.crypto.domain.model.*
import kotlinx.coroutines.flow.Flow

@Suppress("LongParameterList")
interface CoinRepository {
    fun getCoins(
        coinIds: List<String>? = null,
        currency: String,
        priceChangePercentage: String,
        itemOrder: String,
        itemPerPage: Int,
        page: Int
    ): Flow<List<CoinItem>>

    fun getCoinDetail(
        coinId: String,
        localization: Boolean = false,
        tickers: Boolean = false,
        marketData: Boolean = true,
        communityData: Boolean = false,
        developerData: Boolean = false,
        sparkline: Boolean = false
    ): Flow<CoinDetail>

    fun getCoinPrices(
        coinId: String,
        currency: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): Flow<List<CoinPrice>>
}
