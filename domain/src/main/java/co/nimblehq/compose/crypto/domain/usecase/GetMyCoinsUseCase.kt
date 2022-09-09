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

    data class Input(
        val currency: String,
        val order: String,
        val priceChangeInHour: String,
        val itemPerPage: Int,
        val page: Int
    )

    fun execute(
        input: Input
    ): Flow<List<CoinItem>> {
        return with(input) {
            repository.getCoins(
                coinIds = myCoinIds,
                currency = currency,
                priceChangePercentage = priceChangeInHour,
                itemOrder = order,
                itemPerPage = itemPerPage,
                page = page
            )
        }
    }
}
