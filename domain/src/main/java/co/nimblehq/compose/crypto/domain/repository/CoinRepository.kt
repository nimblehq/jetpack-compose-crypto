package co.nimblehq.compose.crypto.domain.repository

import co.nimblehq.compose.crypto.domain.model.CoinItem
import co.nimblehq.compose.crypto.domain.model.CoinPrice
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

    fun getCoinPrices(
        coinId: String,
        currency: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): Flow<List<CoinPrice>>
}
