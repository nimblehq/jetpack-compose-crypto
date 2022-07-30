package co.nimblehq.compose.crypto.domain.repository

import co.nimblehq.compose.crypto.domain.model.CoinItem
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getTrendingList(
        coinIds: List<String>,
        currency: String,
        priceChangePercentage: String,
        itemOrder: String,
        itemPerPage: Int,
        page: Int
    ): Flow<List<CoinItem>>
}
