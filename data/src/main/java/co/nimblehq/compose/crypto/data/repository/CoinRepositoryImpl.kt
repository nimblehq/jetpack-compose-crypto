package co.nimblehq.compose.crypto.data.repository

import co.nimblehq.compose.crypto.data.flowTransform
import co.nimblehq.compose.crypto.data.model.response.CoinItemResponse
import co.nimblehq.compose.crypto.data.model.response.toModels
import co.nimblehq.compose.crypto.data.service.ApiService
import co.nimblehq.compose.crypto.domain.model.CoinItem
import co.nimblehq.compose.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinRepositoryImpl(
    private val api: ApiService
) : CoinRepository {

    override fun getTrendingList(
        coinIds: List<String>,
        currency: String,
        priceChangePercentage: String,
        itemOrder: String,
        itemPerPage: Int,
        page: Int
    ): Flow<List<CoinItem>> = flowTransform<List<CoinItemResponse>> {
        api.getCoins(
            coinIds = coinIds.joinToString(separator = ","),
            currency = currency,
            priceChangePercentage = priceChangePercentage,
            itemOrder = itemOrder,
            itemPerPage = itemPerPage,
            page = page
        )
    }.map { it.toModels() }
}