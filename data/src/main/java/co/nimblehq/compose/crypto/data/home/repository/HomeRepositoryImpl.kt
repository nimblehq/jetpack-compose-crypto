package co.nimblehq.compose.crypto.data.home.repository

import co.nimblehq.compose.crypto.data.flowTransform
import co.nimblehq.compose.crypto.data.home.model.response.toModel
import co.nimblehq.compose.crypto.data.home.service.HomeApiService
import co.nimblehq.compose.crypto.domain.home.HomeRepository
import co.nimblehq.compose.crypto.domain.model.CoinItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val api: HomeApiService
) : HomeRepository {

    override fun getCoins(
        coinIds: List<String>?,
        currency: String,
        priceChangePercentage: String,
        itemOrder: String,
        itemPerPage: Int,
        page: Int
    ): Flow<List<CoinItem>> = flowTransform {
        api.getCoins(
            coinIds = coinIds?.joinToString(separator = ","),
            currency = currency,
            priceChangePercentage = priceChangePercentage,
            itemOrder = itemOrder,
            itemPerPage = itemPerPage,
            page = page
        )
    }.map { coinResponses -> coinResponses.map { it.toModel() } }
}
