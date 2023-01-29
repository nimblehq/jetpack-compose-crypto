package co.nimblehq.compose.crypto.domain.home

import co.nimblehq.compose.crypto.domain.home.model.CoinItem
import co.nimblehq.compose.crypto.domain.model.*
import kotlinx.coroutines.flow.Flow

@Suppress("LongParameterList")
interface HomeRepository {
    fun getCoins(
        coinIds: List<String>? = null,
        currency: String,
        priceChangePercentage: String,
        itemOrder: String,
        itemPerPage: Int,
        page: Int
    ): Flow<List<CoinItem>>
}
