package co.nimblehq.compose.crypto.domain.usecase

import co.nimblehq.compose.crypto.domain.model.CoinPrice
import co.nimblehq.compose.crypto.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinPricesUseCase @Inject constructor(private val repository: CoinRepository) {

    data class Input(
        val coinId: String,
        val currency: String,
        val fromTimestamp: Long,
        val toTimestamp: Long
    )

    fun execute(
        input: Input
    ): Flow<List<CoinPrice>> {
        return with(input) {
            repository.getCoinPrices(
                coinId = coinId,
                currency = currency,
                fromTimestamp = fromTimestamp,
                toTimestamp = toTimestamp
            )
        }
    }
}
