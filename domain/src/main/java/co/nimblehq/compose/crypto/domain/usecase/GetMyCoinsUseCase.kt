package co.nimblehq.compose.crypto.domain.usecase

import co.nimblehq.compose.crypto.domain.model.CoinItem
import co.nimblehq.compose.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyCoinsUseCase @Inject constructor(private val repository: CoinRepository) {

    private val myCoinIds = listOf(
        "bitcoin",
        "ethereum",
        "binancecoin",
        "ripple",
        "cardano",
        "solana",
        "polkadot",
        "near",
        "tron",
        "dogecoin"
    )

    fun execute(
        currency: String,
        order: String,
        priceChangeInHour: String,
        itemPerPage: Int,
        page: Int
    ): Flow<List<CoinItem>> {
        return repository.getTrendingList(
            coinIds = myCoinIds,
            currency = currency,
            priceChangePercentage = priceChangeInHour,
            itemOrder = order,
            itemPerPage = itemPerPage,
            page = page
        )
    }
}
